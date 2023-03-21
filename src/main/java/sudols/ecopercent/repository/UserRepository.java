package sudols.ecopercent.repository;

import sudols.ecopercent.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    // TODO: Spring Data JPA 로 할 경우 updateProfile 이라는 이름은 사용 불가능한데 어떻게 해야할지??
    void updateProfile(Long userId, User newUserData);

    void deleteById(Long userId);

    // Test
    List<User> findAll();

    // Test
    void clearStore();
}
