package sudols.ecopercent.repository;

import org.springframework.stereotype.Repository;
import sudols.ecopercent.domain.User;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static final Map<Long, User> store = new HashMap<>();
    private static long sequeunce = 0L;


    @Override
    public void save(User user) {
        user.setUserId(sequeunce++);
        store.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream().filter(member -> member.getEmail().equals(email))
                .findAny();
    }

//    // TODO: null인 값은 변경하지 않게 해야함. (profileImage를 내릴 경우(null) 어떻게 처리할지?)
//    @Override
//    public void update(Long userId, User newUserData) {
//        System.out.println(newUserData.toString());
//        store.replace(userId, newUserData);
//    }
}
