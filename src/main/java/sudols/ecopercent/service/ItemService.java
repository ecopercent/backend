package sudols.ecopercent.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.RequestPatchItemDetailDto;
import sudols.ecopercent.dto.item.RequestPostItemDto;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    public Item addItem(RequestPostItemDto postItemDto) {
        return userRepository.findById(postItemDto.getUserId())
                .map(user -> {
                    Item item = postItemDto.toEntity(user);
                    return itemRepository.save(item);
                }).get();
    }

    public List<Item> findListByCategory(Long userId, String category) {
        // TODO: 변경. 좀 더 나은 방법을 찾아볼까?
        if (!category.equals("ecobag") && !category.equals("tumbler")) {
            throw new IllegalStateException("존재하지 않는 카테고리입니다.");
        }
        return itemRepository.findByCategoryAndUser_IdOrderById(category, userId);
    }

    public Optional<Item> findItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    public Optional<Item> updateDetail(Long itemId, RequestPatchItemDetailDto pathItemDto) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    BeanUtils.copyProperties(pathItemDto, item, "id");
                    return itemRepository.save(item);
                });
    }

    // TODO: 구현. 마지막 사용횟수 증가
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

    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public void deleteAll() {
        itemRepository.deleteAll();
    }
}
