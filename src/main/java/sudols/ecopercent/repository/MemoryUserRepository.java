package sudols.ecopercent.repository;

import org.springframework.stereotype.Repository;
import sudols.ecopercent.domain.User;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static Map<Long, User> store = new HashMap<>();
    private static long sequeunce = 0L;


    @Override
    public User save(User user) {
        user.setUserId(sequeunce++);
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
