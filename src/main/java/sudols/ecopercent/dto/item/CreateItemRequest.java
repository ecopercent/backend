package sudols.ecopercent.dto.item;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateItemRequest {

    @NotBlank
    @Size(min = 2, max = 8, message = "Item nickname can have a minimum of 2 characters and maximum of 35 characters.")
    @Pattern(regexp = "^(?!\\s)(?!.*\\s$)[\\s\\S]*$", message = "Item nickname cannot contain spaces at the beginning or end.")
    private String nickname;

    private String category;

    @NotBlank
//    @Pattern(regexp = "^(플라스틱|유리|스테인리스|실리콘|면|PVC|종이|기타)$", message = "Invalid type. Allowed values: 플라스틱, 유리, 스테인리스, 실리콘, 면, PVC, 종이 등등")
    private String type;

    @Size(max = 10, message = "Brand can have a maximum of 10 characters.")
    @Pattern(regexp = "^(?!\\s)(?!.*\\s$)[\\s\\S]*$", message = "Brand must have spaces only in the middle.")
    private String brand;

    @Min(value = 0, message = "Price must be at least 0.")
    @Max(value = 99999999, message = "Price cannot exceed 99999999.")
    private Integer price;

    @NotNull(message = "Goal usage count cannot be null.")
    @Min(value = 30, message = "Goal usage count must be at least 30.")
    @Max(value = 3000, message = "Goal usage count cannot exceed 3000.")
    private Integer goalUsageCount;

    @PastOrPresent(message = "Purchase date must be in the past or the present.")
    private LocalDate purchaseDate;
}
