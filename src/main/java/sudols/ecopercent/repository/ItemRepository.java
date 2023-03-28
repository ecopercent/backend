package sudols.ecopercent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sudols.ecopercent.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategoryAndUser_IdOrderById(String category, Long userId);

    Optional<Item> findByCategoryAndIsTitleAndUser_Id(String category, boolean isTitle, Long userId);

    Optional<Item> findByIdAndCategoryAndUser_Id(Long itemId, String category, Long userId);
}
