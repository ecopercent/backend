package sudols.ecopercent.dto.item;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemResponse {

    private Long id;

    private Long userId;

    private String image;

    private String nickname;

    private String category;

    private String type;

    private String brand;

    private Integer price;

    private Long goalUsageCount;

    private Long currentUsageCount;

    private LocalDateTime purchaseDate;

    private LocalDateTime registrationDate;

    private LocalDateTime latestDate;

    private Boolean isTitle;
}
