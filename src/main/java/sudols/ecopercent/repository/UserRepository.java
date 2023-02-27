package sudols.ecopercent.repository;

import sudols.ecopercent.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

//    void update(Long userId, User newUserData);
}
