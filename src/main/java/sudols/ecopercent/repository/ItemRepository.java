package sudols.ecopercent.repository;

import sudols.ecopercent.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);

    List<Item> findItemListByIdAndCategory(Long userId, String category);

    Optional<Item> findById(Long itemId);

    // TODO: refactor. id -> itemId
    void update(Long id, Item newItem);

    Long increaseUsageCount(Long itemId);

    void deleteById(Long itemId);

    List<Item> findAll();

    void clearStore();
}
