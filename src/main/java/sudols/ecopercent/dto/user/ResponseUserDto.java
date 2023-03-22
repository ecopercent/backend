package sudols.ecopercent.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ResponseUserDto {
    private Long userId;
    private String nickname;
    private String email;
    private String profileImage;
    private String profileMessage;
    private Long titleTumblerId;
    private Long titleEcobagId;
}
