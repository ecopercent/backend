package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;

import java.util.List;

public interface ItemService {

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    ItemResponse createItem(HttpServletRequest request, CreateItemRequest createItemRequest, MultipartFile itemImageMultipartFile);

    // TODO: 변경. 좀 더 나은 방법을 찾아볼까?
    List<ItemResponse> getMyItemListByCategory(HttpServletRequest request, String category);

    ItemResponse getItem(Long itemId);

    // TODO: 구현. type 에 따라 goalUsageCount 설정
    ItemResponse updateItem(HttpServletRequest request, Long itemId, UpdateItemRequest updateItemRequest, MultipartFile itemImageMultipartFile);

    // TODO: 구현. 마지막 사용횟수 증가
    ItemResponse increaseUsageCount(HttpServletRequest request, Long itemId);

    ItemResponse changeTitleTumbler(HttpServletRequest request, Long itemId);

    ItemResponse changeTitleEcobag(HttpServletRequest request, Long itemId);

    ItemResponse getTitleTumbler(HttpServletRequest request);

    ItemResponse getTitleEcobag(HttpServletRequest request);

    void deleteItem(Long itemId);

    // TEST
    List<ItemResponse> getAllItemList();

    // TEST
    void deleteAllItem();
}
