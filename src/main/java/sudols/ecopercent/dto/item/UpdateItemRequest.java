package sudols.ecopercent.dto.item;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UpdateItemRequest {
    private String image;

    private String nickname;

    private String type;

    private String brand;

    private Integer price;

    private LocalDate purchaseDate;
}
