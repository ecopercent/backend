package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    ItemResponse createItem(HttpServletRequest request, CreateItemRequest createItemRequest);

    // TODO: 변경. 좀 더 나은 방법을 찾아볼까?
    List<ItemResponse> getItemListByCategory(HttpServletRequest request, String category);

    ItemResponse getItem(Long itemId);

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    ItemResponse updateItem(HttpServletRequest request, Long itemId, UpdateItemRequest updateItemRequest);

    // TODO: 구현. 마지막 사용횟수 증가
    Optional<ItemResponse> increaseUsageCount(Long itemId);

    Optional<ItemResponse> updateTitleTumbler(Long itemId, Long userId);

    Optional<ItemResponse> updateTitleEcobag(Long itemId, Long userId);

    Optional<ItemResponse> getTitleTumbler(Long userId);

    Optional<ItemResponse> getTitleEcobag(Long userId);

    void deleteItem(Long itemId);

    // TEST
    List<ItemResponse> getAllItemList();

    // TEST
    void deleteAllItem();
}
