package sudols.ecopercent.dto.item;

import lombok.Data;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;

import java.util.Date;

@Data
public class CreateItemRequest {
    private Long userId;

    // TODO: image 자료형 뭐로 해야하는지?
    private String image;

    private String nickname;

    private String category;

    private String type;

    private String brand;

    private Integer price;

    private Date purchaseDate;

    public Item toEntity(User user) {
        return Item.builder()
                .user(user)
                .image(image)
                .nickname(nickname)
                .category(category)
                .type(type)
                .brand(brand)
                .price(price)
                .goalUsageCount(1000L) // TODO: 구현. 주어진 type 에 맞게 설정
                .currentUsageCount(0L)
                .isTitle(false)
                .purchaseDate(purchaseDate)
                .build();
    }
}
