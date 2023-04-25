package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ItemResponse createItem(HttpServletRequest request, CreateItemRequest createItemRequest) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(email));
        Item item = itemMapper.createItemRequestToItem(createItemRequest, user);
        item.setRegistrationDate(getKSTDateTime());
        itemRepository.save(item);
        return itemMapper.itemToItemResponse(item);
    }

    @Override
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

    @Override
    public ItemResponse getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistsException(itemId));
        return itemMapper.itemToItemResponse(item);
    }

    @Override
    public ItemResponse updateItem(HttpServletRequest request, Long itemId, UpdateItemRequest updateItemRequest) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    isItemOwnedUserByEmail(request, item);
                    BeanUtils.copyProperties(updateItemRequest, item, "id");
                    Item updateditem = itemRepository.save(item);
                    return itemMapper.itemToItemResponse(updateditem);
                })
                .orElseThrow(() -> new ItemNotExistsException(itemId));
    }

    @Override
    public ItemResponse increaseUsageCount(HttpServletRequest request, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistsException(itemId));
        isItemOwnedUserByEmail(request, item);
        item.setCurrentUsageCount(item.getCurrentUsageCount() + 1);
        item.setLatestDate(getKSTDateTime());
        Item updatedItem = itemRepository.save(item);
        return itemMapper.itemToItemResponse(updatedItem);
    }

    @Override
    public ItemResponse changeTitleTumbler(HttpServletRequest request, Long itemId) {
        return setTitleItem(request, itemId, "tumbler");
    }

    @Override
    public ItemResponse changeTitleEcobag(HttpServletRequest request, Long itemId) {
        return setTitleItem(request, itemId, "ecobag");
    }

    private ItemResponse setTitleItem(HttpServletRequest request, Long itemId, String category) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        itemRepository.findByCategoryAndIsTitleTrueAndUser_Email(category, email)
                .ifPresent(titleItem -> {
                    titleItem.setIsTitle(false);
                });
        return itemRepository.findByIdAndCategory(itemId, category)
                .map(item -> {
                    String itemOwnedUserEmail = item.getUser().getEmail();
                    if (!itemOwnedUserEmail.equals(email)) {
                        throw new UserNotItemOwnedException(itemId);
                    }
                    item.setIsTitle(true);
                    itemRepository.save(item);
                    return itemMapper.itemToItemResponse(item);
                })
                .orElseThrow(() -> new ItemNotExistsException(itemId));
    }

    @Override
    public ItemResponse getTitleTumbler(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        final String category = "tumbler";
        return itemRepository.findByCategoryAndIsTitleTrueAndUser_Email(category, email)
                .map(itemMapper::itemToItemResponse)
                .orElseThrow(() -> new TitleItemNotFoundException(category));
    }

    @Override
    public ItemResponse getTitleEcobag(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        final String category = "ecobag";
        return itemRepository.findByCategoryAndIsTitleTrueAndUser_Email(category, email)
                .map(itemMapper::itemToItemResponse)
                .orElseThrow(() -> new TitleItemNotFoundException(category));
    }

    @Override
    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotExistsException(itemId);
        }
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemResponse> getAllItemList() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::itemToItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllItem() {
        itemRepository.deleteAll();
    }

    private boolean isValidCategory(String category) {
        return category.equals("ecobag") || category.equals("tumbler");
    }

    private void isItemOwnedUserByEmail(HttpServletRequest request, Item item) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        String itemOwnedUserEmail = item.getUser().getEmail();
        if (!itemOwnedUserEmail.equals(email)) {
            throw new UserNotItemOwnedException(item.getId());
        }
    }

    private LocalDateTime getKSTDateTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withNano(0);
    }
}
