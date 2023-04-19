package sudols.ecopercent.dto.user;

import lombok.*;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String nickname;

    private String email;

    private String profileImage;

    private String profileMessage;

    private String oAuthProvider;
}
