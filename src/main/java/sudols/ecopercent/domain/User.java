package sudols.ecopercent.domain;


import lombok.*;

@Getter
@Setter
public class User {
    private Long userId;
    private String nickName;
    private String name;
    private String email;
    private String profileImage;
    private Long titleTumblerId;
    private Long titleEcobagId;
}
