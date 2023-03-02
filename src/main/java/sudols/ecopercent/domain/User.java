package sudols.ecopercent.domain;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "profile_message")
    private String profileMessage;

    @Column(name = "title_tumbler_id")
    private Long titleTumblerId;

    @Column(name = "title_ecobag_id")
    private Long titleEcobagId;
}
