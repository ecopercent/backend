package sudols.ecopercent.repository.jpa;

import jakarta.persistence.EntityManager;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.repository.ItemRepository;

import java.util.List;
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
    public List<Item> findItemListByIdAndCategory(Long userId, String category) {
        List<Item> result = em.createQuery("select i from Item i where i.userId = :userId and i.category = :category")
                .setParameter("userId", userId)
                .setParameter("category", category)
                .getResultList();
        return result ;
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        Item item = em.find(Item.class, itemId);
        return Optional.ofNullable(item);
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
