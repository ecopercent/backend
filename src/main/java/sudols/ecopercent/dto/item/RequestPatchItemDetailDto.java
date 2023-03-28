package sudols.ecopercent.dto.item;

import lombok.*;
import sudols.ecopercent.domain.Item;

import java.util.Date;

@Data
public class RequestPatchItemDetailDto {
    private String image;

    private String nickname;

    private String type;

    private String brand;

    private Integer price;

    private Date purchaseDate;
}
