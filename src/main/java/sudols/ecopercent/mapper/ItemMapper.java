package sudols.ecopercent.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.util.TimeUtil;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final TimeUtil timeUtil;

    public Item createItemRequestToItem(CreateItemRequest createItemRequest, User user) {
        return Item.builder()
                .user(user)
                .nickname(createItemRequest.getNickname())
                .category(createItemRequest.getCategory())
                .type(createItemRequest.getType())
                .brand(createItemRequest.getBrand())
                .price(createItemRequest.getPrice())
                .goalUsageCount(100L)
                .currentUsageCount(0L)
                .isTitle(false)
                .purchaseDate(createItemRequest.getPurchaseDate())
                .registrationDate(timeUtil.getKSTDateTime())
                .latestDate(null)
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
