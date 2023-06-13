package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
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
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.util.TimeUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final TimeUtil timeUtil;

    public ItemResponse createItem(HttpServletRequest request, CreateItemRequest createItemRequest, MultipartFile itemImageMultipartFile) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
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

    public List<ItemResponse> getMyItemListByCategory(HttpServletRequest request, String category) {
        if (!isValidCategory(category)) {
            throw new ItemCategoryNotExistsException(category);
        }
        String email = jwtTokenProvider.getEmailFromRequest(request);
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

    public ItemResponse updateItem(HttpServletRequest request, Long itemId, UpdateItemRequest updateItemRequest, MultipartFile itemImageMultipartFile) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistsException(itemId));
        String email = jwtTokenProvider.getEmailFromRequest(request);
        isItemOwnedUserByEmail(item, email);
        BeanUtils.copyProperties(updateItemRequest, item);
        try {
            item.setImage(itemImageMultipartFile.getBytes());
        } catch (Exception ignore) {
        }
        Item updateditem = itemRepository.save(item);
        return itemMapper.itemToItemResponse(updateditem);
    }

    public ItemResponse increaseUsageCount(HttpServletRequest request, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistsException(itemId));
        String email = jwtTokenProvider.getEmailFromRequest(request);
        isItemOwnedUserByEmail(item, email);
        item.setCurrentUsageCount(item.getCurrentUsageCount() + 1);
        item.setLatestDate(timeUtil.getKSTDateTime());
        Item updatedItem = itemRepository.save(item);
        return itemMapper.itemToItemResponse(updatedItem);
    }

    public ItemResponse changeTitleTumbler(HttpServletRequest request, Long itemId) {
        return setTitleItem(request, itemId, "tumbler");
    }

    public ItemResponse changeTitleEcobag(HttpServletRequest request, Long itemId) {
        return setTitleItem(request, itemId, "ecobag");
    }

    private ItemResponse setTitleItem(HttpServletRequest request, Long itemId, String category) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
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

    public ItemResponse getTitleTumbler(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        final String category = "tumbler";
        return itemRepository.findByCategoryAndIsTitleTrueAndUser_Email(category, email)
                .map(itemMapper::itemToItemResponse)
                .orElseThrow(() -> new TitleItemNotExistsException(category));
    }

    public ItemResponse getTitleEcobag(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        final String category = "ecobag";
        return itemRepository.findByCategoryAndIsTitleTrueAndUser_Email(category, email)
                .map(itemMapper::itemToItemResponse)
                .orElseThrow(() -> new TitleItemNotExistsException(category));
    }

    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotExistsException(itemId);
        }
        itemRepository.deleteById(itemId);
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
