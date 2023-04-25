package sudols.ecopercent.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.NonNullApi;
import sudols.ecopercent.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategoryAndUser_EmailOrderById(String category, String email);

    Optional<Item> findByIdAndCategory(Long itemId, String category);

    Optional<Item> findByIdAndUser_Email(Long itemId, String email);

    @Query("select i from Item i where i.category = ?1 and i.isTitle = true and i.user.email = ?2")
    Optional<Item> findByCategoryAndIsTitleTrueAndUser_Email(String category, String email);

    boolean existsById(@NonNull Long itemId);

    void deleteByUser_Email(String email);
}
