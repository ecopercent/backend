package sudols.ecopercent.service;

import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.ItemPostDto;
import sudols.ecopercent.repository.ItemRepository;

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
}
