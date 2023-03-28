package sudols.ecopercent.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Item createItem(CreateItemRequest postItemDto) {
        return userRepository.findById(postItemDto.getUserId())
                .map(user -> {
                    Item item = postItemDto.toEntity(user);
                    return itemRepository.save(item);
                }).get();
    }

    public List<Item> getItemList(Long userId, String category) {
        if (!category.equals("ecobag") && !category.equals("tumbler")) {
            throw new IllegalStateException("존재하지 않는 카테고리입니다.");
        }
        return itemRepository.findByCategoryAndUser_IdOrderById(category, userId);
    }

    public Optional<Item> getItem(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public Optional<Item> updateItem(Long itemId, UpdateItemRequest pathItemDto) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    BeanUtils.copyProperties(pathItemDto, item, "id");
                    return itemRepository.save(item);
                });
    }

    public Optional<Item> increaseUsageCount(Long itemId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    item.setCurrentUsageCount(item.getCurrentUsageCount() + 1);
                    return itemRepository.save(item);
                });
    }

    public Optional<Item> updateTitleTumbler(Long itemId, Long userId) {
        return updateTitleItem(itemId, "tumbler", userId);
    }


    public Optional<Item> updateTitleEcobag(Long itemId, Long userId) {
        return updateTitleItem(itemId, "ecobag", userId);
    }

    private Optional<Item> updateTitleItem(Long itemId, String category, Long userId) {
        return itemRepository.findByIdAndCategoryAndUser_Id(itemId, category, userId)
                .map(item -> {
                    itemRepository.findByCategoryAndIsTitleAndUser_Id(category, true, userId)
                            .ifPresent(titleItem -> {
                                titleItem.setIsTitle(false);
                                itemRepository.save(titleItem);
                            });
                    item.setIsTitle(true);
                    return itemRepository.save(item);
                });
    }

    public Optional<Item> getTitleTumbler(Long userId) {
        return itemRepository.findByCategoryAndIsTitleAndUser_Id("tumbler", true, userId);
    }

    public Optional<Item> getTitleEcobag(Long userId) {
        return itemRepository.findByCategoryAndIsTitleAndUser_Id("ecobag", true, userId);
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public List<Item> getAllItemList() {
        return itemRepository.findAll();
    }

    public void deleteAllItem() {
        itemRepository.deleteAll();
    }
}
