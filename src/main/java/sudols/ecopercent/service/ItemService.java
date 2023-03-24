package sudols.ecopercent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
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

    public Long addItem(RequestPostItemDto itemDto) {
        Item item = itemDto.toEntity();
        return itemRepository.save(itemDto.getUserId(), item).getId();
    }

    public List<Item> findListByCategory(Long userId, String category) {
        validateExistUserByUserId(userId);
        // TODO: 확장성이 안좋으므로 나중에 수정
        if (category.equals("ecobag") == false && category.equals("tumbler") == false) {
            throw new IllegalStateException("존재하지 않는 카테고리입니다.");
        }
        return itemRepository.findListByIdAndCategory(userId, category);
    }

    public Optional<Item> findOne(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public void updateDetail(Long itemId, RequestPatchItemDetailDto newItemData) {
        validateExistItemByItemId(itemId);
        itemRepository.update(itemId, newItemData.toEntity());
    }

    public Long increaseUsageCount(Long itemId) {
        validateExistItemByItemId(itemId);
        return itemRepository.increaseUsageCount(itemId);
    }

    public void deleteOne(Long itemId) {
        validateExistItemByItemId(itemId);
        itemRepository.deleteById(itemId);
    }

    public void updateTitleTumbler(Long userId, Long itemId) {
        itemRepository.updateTitleItem(userId, itemId, "tumbler");
    }

    public void updateTitleEcobag(Long userId, Long itemId) {
        itemRepository.updateTitleItem(userId, itemId, "ecobag");
    }

    public Optional<Item> getTitleTumbler(Long userId) {
        return itemRepository.getTitleItem(userId, "tumbler");
    }

    public Optional<Item> getTitleEcobag(Long userId) {
        return itemRepository.getTitleItem(userId, "ecobag");
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public void deleteAll() {
        itemRepository.clearStore();
    }

    private void validateExistItemByOptional(Optional<Item> optionalItem) {
        if (optionalItem.isPresent() == false) {
            throw new IllegalStateException("존재하지 않는 아이템입니다.");
        }
    }

    private void validateExistItemByItemId(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        validateExistItemByOptional(optionalItem);
    }

    private void validateExistUserByOptionalUser(Optional<User> optionalUser) {
        if (optionalUser.isPresent() == false) {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        }
    }

    private void validateExistUserByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        validateExistUserByOptionalUser(optionalUser);
    }
}
