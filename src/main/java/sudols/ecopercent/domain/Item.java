package sudols.ecopercent.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image")
    private String image;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "category")
    private String category;

    @Column(name = "type")
    private String type;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private Integer price;

    @Column(name = "goal_usage_count")
    private Long goalUsageCount;

    @Column(name = "current_usage_count")
    private Long currentUsageCount;

    @Column(name = "purchase_data")
    private Date purchaseDate;

    // TODO: date 형식 정하는게 있는지 찾아보기. 어노테이션
    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "latest_data")
    private Date latestDate;

    private Boolean isTitle;
}