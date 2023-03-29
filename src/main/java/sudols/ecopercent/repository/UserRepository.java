package sudols.ecopercent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sudols.ecopercent.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
