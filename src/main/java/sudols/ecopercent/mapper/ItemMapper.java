package sudols.ecopercent.mapper;

import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;

public class ItemMapper {

    // TODO: 구현. 주어진 type 에 맞게 설정
    public Item createItemRequestToItem(CreateItemRequest request, User user) {
        return Item.builder()
                .user(user)
                .image(request.getImage())
                .nickname(request.getNickname())
                .category(request.getCategory())
                .type(request.getType())
                .brand(request.getBrand())
                .price(request.getPrice())
                .goalUsageCount(1000L)
                .currentUsageCount(0L)
                .isTitle(false)
                .purchaseDate(request.getPurchaseDate())
                .build();
    }

    public ItemResponse itemToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .image(item.getImage())
                .nickname(item.getNickname())
                .category(item.getCategory())
                .type(item.getType())
                .brand(item.getBrand())
                .price(item.getPrice())
                .goalUsageCount(item.getGoalUsageCount())
                .currentUsageCount(item.getCurrentUsageCount())
                .purchaseDate(item.getPurchaseDate())
                .registrationDate(item.getRegistrationDate())
                .latestDate(item.getLatestDate())
                .isTitle(item.getIsTitle())
                .build();
    }
}
