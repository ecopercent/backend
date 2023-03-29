package sudols.ecopercent.dto.item;

import lombok.Data;

import java.time.LocalDateTime;
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

    private LocalDateTime purchaseDate;
}
