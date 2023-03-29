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
}
