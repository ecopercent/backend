package sudols.ecopercent.repository;

import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);

    List<Item> findListByIdAndCategory(Long userId, String category);

    Optional<Item> findById(Long itemId);

    // TODO: refactor. id -> itemId
    void update(Long id, Item newItem);

    Long increaseUsageCount(Long itemId);

    void deleteById(Long itemId);

    void updateTitleItem(Long userId, Long itemId, String category);

    Optional<Item> getTitleItem(Long userId, String category);

    List<Item> findAll();

    void clearStore();
}
