package sudols.ecopercent.dto.user;

import lombok.*;

@Data
public class UserResponse {

    private Long userId;

    private String nickname;

    private String email;

    private String profileImage;

    private String profileMessage;
}
