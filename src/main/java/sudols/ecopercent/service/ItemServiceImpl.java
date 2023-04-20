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
import sudols.ecopercent.exception.ItemCategoryNotExistException;
import sudols.ecopercent.exception.ItemNotExistException;
import sudols.ecopercent.exception.UserNotExistException;
import sudols.ecopercent.mapper.ItemMapper;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new UserNotExistException(email));
        Item item = itemMapper.createItemRequestToItem(createItemRequest, user);
        item.setRegistrationDate(getKSTDateTime());
        itemRepository.save(item);
        return itemMapper.itemToItemResponse(item);
    }

    @Override
    public List<ItemResponse> getItemListByCategory(HttpServletRequest request, String category) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        if (!isValidCategory(category)) {
            throw new ItemCategoryNotExistException(category);
        }
        return itemRepository.findByCategoryAndUser_EmailOrderById(category, email)
                .stream()
                .map(itemMapper::itemToItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponse getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotExistException(itemId));
        return itemMapper.itemToItemResponse(item);
    }

    @Override
    public ItemResponse updateItem(Long itemId, UpdateItemRequest updateItemRequest) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    BeanUtils.copyProperties(updateItemRequest, item, "id");
                    Item updateditem = itemRepository.save(item);
                    return itemMapper.itemToItemResponse(updateditem);
                })
                .orElseThrow(() -> new ItemNotExistException(itemId));
    }

    @Override
    public Optional<ItemResponse> increaseUsageCount(Long itemId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    item.setCurrentUsageCount(item.getCurrentUsageCount() + 1);
                    item.setLatestDate(getKSTDateTime());
                    return itemRepository.save(item);
                })
                .map(itemMapper::itemToItemResponse);
    }

    @Override
    public Optional<ItemResponse> updateTitleTumbler(Long itemId, Long userId) {
        return updateTitleItem(itemId, "tumbler", userId);
    }


    @Override
    public Optional<ItemResponse> updateTitleEcobag(Long itemId, Long userId) {
        return updateTitleItem(itemId, "ecobag", userId);
    }

    @Override
    public Optional<ItemResponse> getTitleTumbler(Long userId) {
        return itemRepository.findByCategoryAndIsTitleAndUser_Id("tumbler", true, userId)
                .map(itemMapper::itemToItemResponse);
    }

    @Override
    public Optional<ItemResponse> getTitleEcobag(Long userId) {
        return itemRepository.findByCategoryAndIsTitleAndUser_Id("ecobag", true, userId)
                .map(itemMapper::itemToItemResponse);
    }

    @Override
    public void deleteItem(Long itemId) {
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

    private Optional<ItemResponse> updateTitleItem(Long itemId, String category, Long userId) {
        return itemRepository.findByIdAndCategoryAndUser_Id(itemId, category, userId)
                .map(item -> {
                    itemRepository.findByCategoryAndIsTitleAndUser_Id(category, true, userId)
                            .ifPresent(titleItem -> {
                                titleItem.setIsTitle(false);
                                itemRepository.save(titleItem);
                            });
                    item.setIsTitle(true);
                    return itemRepository.save(item);
                })
                .map(itemMapper::itemToItemResponse);
    }

    private LocalDateTime getKSTDateTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withNano(0);
    }

    private boolean isValidCategory(String category) {
        return category.equals("ecobag") || category.equals("tumbler");
    }

    private boolean isItemExist(Long itemId) {
        return false;
    }
}
