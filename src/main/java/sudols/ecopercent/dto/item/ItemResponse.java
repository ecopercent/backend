package sudols.ecopercent.dto.item;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemResponse {

    private Long id;

    private Long userId;

    private byte[] image;

    private String nickname;

    private String category;

    private String type;

    private String brand;

    private Integer price;

    private Integer goalUsageCount;

    private Integer currentUsageCount;

    private Integer usageCountPerDay;

    private LocalDate purchaseDate;

    private LocalDateTime registrationDate;

    private LocalDateTime latestDate;

    private Boolean isTitle;
}
