package sudols.ecopercent.repository;

import sudols.ecopercent.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    List<User> findAll();
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void update(Long userId, User newUserData);

    void deleteById(Long userId);

    void clearStore();
}
