package sudols.ecopercent.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
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
    public Item save(Long userId, Item item) {
        User user = findUserByUserId(userId);
        if (item.getGoalUsageCount() == null) {
            item.setGoalUsageCount(100L);
        }
        item.setCurrentUsageCount(0L);
        item.setIsTitle(false);
        item.setUser(user);
        em.persist(item);
        return item;
    }

    @Override
    public List<Item> findListByIdAndCategory(Long userId, String category) {
        User user = findUserByUserId(userId);
        return em.createQuery("select i from Item i where i.user = :user and i.category = :category order by i.id asc")
                .setParameter("user", user)
                .setParameter("category", category)
                .getResultList();
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
        if (newItemData.getGoalUsageCount() == null) {
            item.setGoalUsageCount(100L);
        } else {
            item.setGoalUsageCount(newItemData.getGoalUsageCount());
        }
        item.setPurchaseDate(newItemData.getPurchaseDate());
    }

    // TODO: 생각. naming 괜찮은 걸까?
    @Override
    public Long increaseUsageCount(Long itemId) {
        Item item = em.find(Item.class, itemId);
        Long usageCount = item.getCurrentUsageCount() + 1;
        item.setCurrentUsageCount(usageCount);
        return usageCount;
    }

    @Override
    public void deleteById(Long itemId) {
        Optional<Item> optionalItem = findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        }

        em.remove(optionalItem.get());
    }

    @Override
    public void updateTitleItem(Long userId, Long itemId, String category) {
        User user = findUserByUserId(userId);

        // 기존 대표 아이템의 is_title 을 false 로 변경
        Optional<Item> titleItem = em.createQuery(
                        "select i from Item i where i.user = :user and i.category = :category and i.isTitle = :isTitle", Item.class)
                .setParameter("user", user)
                .setParameter("category", category)
                .setParameter("isTitle", true)
                .getResultList().stream().findAny();
        if (titleItem.isPresent()) {
            titleItem.get().setIsTitle(false);
        }

        // itemId 의 is_title 을 true 로 변경
        Optional<Item> item = em.createQuery(
                        "select i from Item i where i.user = :user and i.category = :category and i.id = :id", Item.class)
                .setParameter("user", user)
                .setParameter("category", category)
                .setParameter("id", itemId)
                .getResultList().stream().findAny();
        if (item.isPresent()) {
            item.get().setIsTitle(true);
        }
    }

    @Override
    public Optional<Item> getTitleItem(Long userId, String category) {
        User user = findUserByUserId(userId);
        return em.createQuery("select i from Item i where i.user = :user and i.category = :category and i.isTitle = :isTitle", Item.class)
                .setParameter("user", user)
                .setParameter("category", category)
                .setParameter("isTitle", true)
                .getResultList().stream().findAny();
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("select i from Item i")
                .getResultList();
    }

    @Override
    public void clearStore() {
        Query query = em.createQuery("delete from Item");
        query.executeUpdate();
    }

    private User findUserByUserId(Long userId) {
        Optional<User> optionalUser = em.createQuery("select u from User u where u.id = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream().findAny();
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        }
        return optionalUser.get();
    }
}
