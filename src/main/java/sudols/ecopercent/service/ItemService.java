package sudols.ecopercent.service;

import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.ItemPatchDetailDto;
import sudols.ecopercent.dto.item.ItemPostDto;
import sudols.ecopercent.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Long add(ItemPostDto item) {
        Item itemEntity = item.toEntity();
        return itemRepository.save(itemEntity).getId();
    }

    public List<Item> findItemList(Long userId, String category) {
        return itemRepository.findItemListByIdAndCategory(userId, category);
    }

    public Optional<Item> findOne(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public void updateItemDetail(Long itemId, ItemPatchDetailDto newItemData) {
        Item itemEntity = newItemData.toEntity();
        itemRepository.update(itemId, itemEntity);
    }


}
