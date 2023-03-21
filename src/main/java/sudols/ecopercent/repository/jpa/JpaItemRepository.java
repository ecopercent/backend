package sudols.ecopercent.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaItemRepository implements ItemRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Item save(Item item) {
        item.setUsageCount(0L);
        em.persist(item);
        return item;
    }

    @Override
    public List<Item> findListByIdAndCategory(Long userId, String category) {
        List<Item> result = em.createQuery("select i from Item i where i.userId = :userId and i.category = :category")
                .setParameter("userId", userId)
                .setParameter("category", category)
                .getResultList();
        return result;
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        Item item = em.find(Item.class, itemId);
        return Optional.ofNullable(item);
    }

    // TODO: 더 나은 방법을 찾아보자
    @Override
    public void update(Long itemId, Item newItemData) {
        Item item = em.find(Item.class, itemId);
        item.setImage(newItemData.getImage());
        item.setNickname(newItemData.getNickname());
        item.setType(newItemData.getType());
        item.setBrand(newItemData.getBrand());
        item.setPrice(newItemData.getPrice());
        item.setPurchaseDate(newItemData.getPurchaseDate());
    }

    // TODO: 생각. naming 괜찮은 걸까?
    @Override
    public Long increaseUsageCount(Long itemId) {
        Item item = em.find(Item.class, itemId);
        Long usageCount = item.getUsageCount() + 1;
        item.setUsageCount(usageCount);
        return usageCount;
    }

    @Override
    public void deleteById(Long itemId) {
        Item item = findById(itemId).get();
        em.remove(item);
    }

    @Override
    public void updateTitleItem(Long userId, Long itemId, String category) {
        // 기존 대표 아이템의 is_title 을 false 로 변경
        Optional<Item> titleItem = em.createQuery(
                        "select i from Item i where i.userId = :userId and i.category = :category and i.isTitle = :isTitle", Item.class)
                .setParameter("userId", userId)
                .setParameter("category", category)
                .setParameter("isTitle", true)
                .getResultList().stream().findAny();
        if (titleItem.isEmpty() == false)
            titleItem.get().setIsTitle(false);

        // itemId 의 is_title 을 true 로 변경
        Optional<Item> item = em.createQuery(
                        "select i from Item i where i.userId = :userId and i.category = :category and i.id = :id", Item.class)
                .setParameter("userId", userId)
                .setParameter("category", category)
                .setParameter("id", itemId)
                .getResultList().stream().findAny();
        item.get().setIsTitle(true);
    }

    @Override
    public Optional<Item> getTitleItem(Long userId, String category) {
        return em.createQuery("select i from Item i where i.userId = :userId and i.category = :category and i.isTitle = :isTitle", Item.class)
                .setParameter("userId", userId)
                .setParameter("category", category)
                .setParameter("isTitle", true)
                .getResultList().stream().findAny();
    }

    @Override
    public List<Item> findAll() {
        List<Item> itemList = em.createQuery("select i from Item i")
                .getResultList();
        return itemList;
    }

    @Override
    public void clearStore() {
        Query query = em.createQuery("delete from Item");
        query.executeUpdate();
    }
}
