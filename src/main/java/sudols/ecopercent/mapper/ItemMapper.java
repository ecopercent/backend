package sudols.ecopercent.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.util.ImageUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final ImageUtil imageUtil;

    // TODO: 구현. 주어진 type 에 맞게 설정
    public Item createItemRequestToItem(CreateItemRequest request, User user) {
        return Item.builder()
                .user(user)
                .nickname(request.getNickname())
                .category(request.getCategory())
                .type(request.getType())
                .brand(request.getBrand())
                .price(request.getPrice())
                .goalUsageCount(100L)
                .currentUsageCount(0L)
                .isTitle(false)
                .purchaseDate(request.getPurchaseDate())
                .latestDate(null)
                .build();
    }

    public ItemResponse itemToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .image(imageUtil.byteaToBase64(item.getImage()))
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
