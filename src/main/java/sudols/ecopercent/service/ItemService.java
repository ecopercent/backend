package sudols.ecopercent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.exception.*;
import sudols.ecopercent.mapper.ItemMapper;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.util.TimeUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final TimeUtil timeUtil;

    public ItemResponse createItem(String email, CreateItemRequest createItemRequest, MultipartFile itemImageMultipartFile) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(email));
        Item item = itemMapper.createItemRequestToItem(createItemRequest, user);
        try {
            item.setImage(itemImageMultipartFile.getBytes());
        } catch (Exception e) {
            item.setImage(null);
        }
        itemRepository.save(item);
        return itemMapper.itemToItemResponse(item);
    }

    public List<ItemResponse> getMyItemListByCategory(String email, String category) {
        if (!isValidCategory(category)) {
            throw new ItemCategoryNotExistsException(category);
        }
        return itemRepository.findByCategoryAndUser_EmailOrderById(category, email)
                .stream()
                .map(itemMapper::itemToItemResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistsException(itemId));
        return itemMapper.itemToItemResponse(item);
    }

    public ItemResponse updateItem(String email, Long itemId, UpdateItemRequest updateItemRequest, MultipartFile itemImageMultipartFile) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistsException(itemId));
        isItemOwnedUserByEmail(item, email);
        BeanUtils.copyProperties(updateItemRequest, item);
        try {
            item.setImage(itemImageMultipartFile.getBytes());
        } catch (Exception ignore) {
        }
        Item updateditem = itemRepository.save(item);
        return itemMapper.itemToItemResponse(updateditem);
    }

    public ItemResponse increaseUsageCount(String email, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistsException(itemId));
        isItemOwnedUserByEmail(item, email);
        if (item.getUsageCountPerDay() >= 3) {
            return itemMapper.itemToItemResponse(item);
        }
        item.setCurrentUsageCount(item.getCurrentUsageCount() + 1);
        item.setUsageCountPerDay(item.getUsageCountPerDay() + 1);
        item.setLatestDate(timeUtil.getKSTDateTime());
        Item updatedItem = itemRepository.save(item);
        return itemMapper.itemToItemResponse(updatedItem);
    }

    public ItemResponse changeTitleTumbler(String email, Long itemId) {
        return setTitleItem(email, itemId, "tumbler");
    }

    public ItemResponse changeTitleEcobag(String email, Long itemId) {
        return setTitleItem(email, itemId, "ecobag");
    }

    private ItemResponse setTitleItem(String email, Long itemId, String category) {
        itemRepository.findByCategoryAndIsTitleTrueAndUser_Email(category, email)
                .ifPresent(prevTitleItem -> {
                    prevTitleItem.setIsTitle(false);
                    itemRepository.save(prevTitleItem);
                });
        return itemRepository.findById(itemId)
                .map(item -> {
                    isCategoryMatching(item, category);
                    isItemOwnedUserByEmail(item, email);
                    item.setIsTitle(true);
                    itemRepository.save(item);
                    return itemMapper.itemToItemResponse(item);
                })
                .orElseThrow(() -> new ItemNotExistsException(itemId));
    }

    public ItemResponse getTitleTumbler(String email) {
        final String category = "tumbler";
        return getTitleItem(email, category);
    }

    public ItemResponse getTitleEcobag(String email) {
        final String category = "ecobag";
        return getTitleItem(email, category);
    }

    private ItemResponse getTitleItem(String email, String category) {
        return itemRepository.findByCategoryAndIsTitleTrueAndUser_Email(category, email)
                .map(item -> {
                    if (item.getLatestDate() != null && !LocalDate.from(item.getLatestDate()).equals(LocalDate.from(timeUtil.getKSTDateTime()))) {
                        item.setUsageCountPerDay(0);
                        itemRepository.save(item);
                    }
                    return itemMapper.itemToItemResponse(item);
                })
                .orElseThrow(() -> new TitleItemNotExistsException(category));
    }

    public void deleteItem(String email, Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotExistsException(itemId);
        }
        itemRepository.findById(itemId)
                .map(item -> {
                    isItemOwnedUserByEmail(item, email);
                    itemRepository.delete(item);
                    return null;
                });
    }

    public List<ItemResponse> getAllItemList() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::itemToItemResponse)
                .collect(Collectors.toList());
    }

    public void deleteAllItem() {
        itemRepository.deleteAll();
    }

    private boolean isValidCategory(String category) {
        return category.equals("ecobag") || category.equals("tumbler");
    }

    private void isItemOwnedUserByEmail(Item item, String email) {
        String itemOwnedUserEmail = item.getUser().getEmail();
        if (!itemOwnedUserEmail.equals(email)) {
            throw new UserNotItemOwnedException(item.getId());
        }
    }

    private void isCategoryMatching(Item item, String category) {
        if (!item.getCategory().equals(category)) {
            throw new CategoryMismatchException(item.getId());
        }
    }
}
