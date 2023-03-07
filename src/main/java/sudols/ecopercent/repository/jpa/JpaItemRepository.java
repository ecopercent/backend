package sudols.ecopercent.repository.jpa;

import jakarta.persistence.EntityManager;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.repository.ItemRepository;

import java.util.Optional;

public class JpaItemRepository implements ItemRepository {

    private final EntityManager em;

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Long id, Item newItem) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void clearStore() {

    }
}
