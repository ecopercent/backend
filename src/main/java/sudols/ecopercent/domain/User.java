package sudols.ecopercent.domain;


import lombok.*;

@Getter
@Setter
@ToString
public class User {
    private Long userId;
    private String nickName;
    private String email;
    private String profileImage;
    private String profileMessage;
    private Long titleTumblerId;
    private Long titleEcobagId;
}
