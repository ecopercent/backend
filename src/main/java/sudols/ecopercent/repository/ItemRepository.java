package sudols.ecopercent.repository;

import sudols.ecopercent.domain.Item;

import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);

    Optional<Item> findById(Long id);

    void update(Long id, Item newItem);

    void deleteById(Long id);

    void clearStore();
}
