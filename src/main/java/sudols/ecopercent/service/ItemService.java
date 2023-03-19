package sudols.ecopercent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.item.ItemPatchDetailDto;
import sudols.ecopercent.dto.item.ItemPostDto;
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

    public Long add(ItemPostDto item) {
        Item itemEntity = item.toEntity();
        return itemRepository.save(itemEntity).getId();
    }

    public List<Item> findListByCategory(Long userId, String category) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent() == false) {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        }
        // TODO: 확장성이 안좋으므로 나중에 수정
        if (category.equals("ecobag") == false && category.equals("tumbler") == false) {
            throw new IllegalStateException("존재하지 않는 카테고리입니다.");
        }
        return itemRepository.findListByIdAndCategory(userId, category);
    }

    public Optional<Item> findOne(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public void updateDetail(Long itemId, ItemPatchDetailDto newItemData) {
        validateExistItemById(itemId);
        itemRepository.update(itemId, newItemData.toEntity());
    }

    public Long increaseUsageCount(Long itemId) {
        validateExistItemById(itemId);
        return itemRepository.increaseUsageCount(itemId);
    }

    public void deleteOne(Long itemId) {
        validateExistItemById(itemId);
        itemRepository.deleteById(itemId);
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

    private void validateExistItemById(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        validateExistItemByOptional(optionalItem);
    }
}
