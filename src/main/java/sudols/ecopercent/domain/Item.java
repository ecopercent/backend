package sudols.ecopercent.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Item {
    private Long id;
    private Long usageCount;
    private Integer price;
    private String image;
    private String nickname;
    private String brand;
    private Date registrationDate;
    private Date purchaseDate;
    private Date lastestDate;

    @Builder
    public Item(Long id, Long usageCount, Integer price, String image, String nickname, String brand, Date registrationDate, Date purchaseDate, Date lastestDate) {
        this.id = id;
        this.usageCount = usageCount;
        this.price = price;
        this.image = image;
        this.nickname = nickname;
        this.brand = brand;
        this.registrationDate = registrationDate;
        this.purchaseDate = purchaseDate;
        this.lastestDate = lastestDate;
    }
}
