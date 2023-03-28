package sudols.ecopercent.service;

import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.dto.item.CreateItemRequest;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    Item createItem(CreateItemRequest postItemDto);

    // TODO: 변경. 좀 더 나은 방법을 찾아볼까?
    List<Item> getItemList(Long userId, String category);

    Optional<Item> getItem(Long itemId);

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    Optional<Item> updateItem(Long itemId, UpdateItemRequest pathItemDto);

    // TODO: 구현. 마지막 사용횟수 증가
    Optional<Item> increaseUsageCount(Long itemId);

    Optional<Item> updateTitleTumbler(Long itemId, Long userId);

    Optional<Item> updateTitleEcobag(Long itemId, Long userId);

    Optional<Item> getTitleTumbler(Long userId);

    Optional<Item> getTitleEcobag(Long userId);

    void deleteItem(Long itemId);

    // TEST
    List<Item> getAllItemList();

    // TEST
    void deleteAllItem();
}
