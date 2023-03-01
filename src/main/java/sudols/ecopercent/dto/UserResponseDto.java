package sudols.ecopercent.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    private String profileImage;
    private String profileMessage;
    private Long titleTumblerId;
    private Long titleEcobagId;
}
