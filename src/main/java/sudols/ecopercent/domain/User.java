package sudols.ecopercent.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long userId;
    private String nickname;
    private String name;
    private String email;
    private String profileImage;
    private Long titleTumblerId;
    private Long titleEcobagId;

    @Builder
    public User(Long userId, String nickname, String name, String email, String profileImage, Long titleTumblerId, Long titleEcobagId) {
        this.userId = userId;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.titleTumblerId = titleTumblerId;
        this.titleEcobagId = titleEcobagId;
    }
}
