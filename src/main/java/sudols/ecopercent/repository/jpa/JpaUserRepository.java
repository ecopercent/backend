package sudols.ecopercent.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> result = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findAny();
    }


    @Override
    public void updateProfile(Long userId, User newUserData) {
        User user = em.find(User.class, userId);
        user.setNickname(newUserData.getNickname());
        user.setProfileImage(newUserData.getProfileImage());
        user.setProfileMessage(newUserData.getProfileMessage());
    }

    @Override
    public void updateTitleTumbler(Long userId, User newTitleData) {
        User user = em.find(User.class, userId);
        // TODO: 구현. 해당 item 이 존재하는지 여부 확인
        user.setTitleTumblerId(newTitleData.getTitleTumblerId());
    }

    @Override
    public void updateTitleEcobag(Long userId, User newTitleData) {
        User user = em.find(User.class, userId);
        // TODO: 구현. 해당 item 이 존재하는지 여부 확인
        user.setTitleEcobagId(newTitleData.getTitleEcobagId());
    }

    @Override
    public void deleteById(Long userId) {
        User user = findById(userId).get();
        em.remove(user);
    }

    @Override
    public List<User> findAll() {
        List<User> result = em.createQuery("select u from User u")
                .getResultList();
        System.out.println(result);
        return result;
    }

    @Override
    public void clearStore() {
        em.clear();
    }
}
