package sudols.ecopercent.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.mapper.ItemMapper;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
    }

    public ItemResponse createItem(CreateItemRequest createItemRequest) {
        return userRepository.findById(createItemRequest.getUserId())
                .map(user -> {
                    Item item = itemMapper.createItemRequestToItem(createItemRequest, user);
                    item.setRegistrationDate(getKSTDateTime());
                    return itemRepository.save(item);
                })
                .map(itemMapper::itemToItemResponse).get();
    }

    public List<ItemResponse> getItemList(Long userId, String category) {
        if (!category.equals("ecobag") && !category.equals("tumbler")) {
            throw new IllegalStateException("존재하지 않는 카테고리입니다.");
        }
        return itemRepository.findByCategoryAndUser_IdOrderById(category, userId)
                .stream()
                .map(itemMapper::itemToItemResponse)
                .collect(Collectors.toList());
    }

    public Optional<ItemResponse> getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .map(itemMapper::itemToItemResponse);
    }

    public Optional<ItemResponse> updateItem(Long itemId, UpdateItemRequest updateItemRequest) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    BeanUtils.copyProperties(updateItemRequest, item, "id");
                    return itemRepository.save(item);
                })
                .map(itemMapper::itemToItemResponse);
    }

    public Optional<ItemResponse> increaseUsageCount(Long itemId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    item.setCurrentUsageCount(item.getCurrentUsageCount() + 1);
                    item.setLatestDate(getKSTDateTime());
                    return itemRepository.save(item);
                })
                .map(itemMapper::itemToItemResponse);
    }

    public Optional<ItemResponse> updateTitleTumbler(Long itemId, Long userId) {
        return updateTitleItem(itemId, "tumbler", userId);
    }


    public Optional<ItemResponse> updateTitleEcobag(Long itemId, Long userId) {
        return updateTitleItem(itemId, "ecobag", userId);
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

    public Optional<ItemResponse> getTitleTumbler(Long userId) {
        return itemRepository.findByCategoryAndIsTitleAndUser_Id("tumbler", true, userId)
                .map(itemMapper::itemToItemResponse);
    }

    public Optional<ItemResponse> getTitleEcobag(Long userId) {
        return itemRepository.findByCategoryAndIsTitleAndUser_Id("ecobag", true, userId)
                .map(itemMapper::itemToItemResponse);
    }

    public void deleteItem(Long itemId) {
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

    private LocalDateTime getKSTDateTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}
