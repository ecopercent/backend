package sudols.ecopercent.dto.item;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

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

    private Date purchaseDate;

    private Date registrationDate;

    private Date latestDate;

    private Boolean isTitle;
}
