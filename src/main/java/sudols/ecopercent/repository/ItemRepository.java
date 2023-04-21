package sudols.ecopercent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sudols.ecopercent.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategoryAndUser_EmailOrderById(String category, String email);

    Optional<Item> findByIdAndCategory(Long itemId, String category);

    @Query("select i from Item i where i.category = ?1 and i.isTitle = true and i.user.email = ?2")
    Optional<Item> findByCategoryAndIsTitleTrueAndUser_Email(String category, String email);

    void deleteByUser_Email(String email);
}
