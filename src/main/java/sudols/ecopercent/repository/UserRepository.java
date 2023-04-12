package sudols.ecopercent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sudols.ecopercent.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
