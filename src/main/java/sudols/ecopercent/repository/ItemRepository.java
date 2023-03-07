package sudols.ecopercent.repository;

import sudols.ecopercent.domain.Item;

import java.util.List;

public interface ItemRepository {
    Item save(Item item);

    List<Item> findItemListByIdAndCategory(Long userId, String category);

    void update(Long id, Item newItem);

    void deleteById(Long id);

    void clearStore();
}
