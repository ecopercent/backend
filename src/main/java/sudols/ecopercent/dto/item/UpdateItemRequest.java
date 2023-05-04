package sudols.ecopercent.dto.item;

import lombok.*;

import java.time.LocalDate;
@Data
public class UpdateItemRequest {

    private byte[] image;

    private String nickname;

    private String type;

    private String brand;

    private Integer price;

    private LocalDate purchaseDate;
}
