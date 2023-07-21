package sudols.ecopercent.dto.item;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
@Data
public class UpdateItemRequest {

    @NotBlank
    @Size(min = 2, max = 8, message = "Item nickname can have a minimum of 2 characters and maximum of 35 characters.")
    @Pattern(regexp = "^(?!\\s)(?!.*\\s$)[\\s\\S]*$", message = "Item nickname cannot contain spaces at the beginning or end.")
    private String nickname;

    @NotBlank
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
