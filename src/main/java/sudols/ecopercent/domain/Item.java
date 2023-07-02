package sudols.ecopercent.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image", length = 56000)
    private byte[] image;

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

    @Column(name = "usage_count_per_day")
    private Long usageCountPerDay;

    @Column(name = "purchase_data")
    private LocalDate purchaseDate;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "latest_data")
    private LocalDateTime latestDate;

    private Boolean isTitle;
}