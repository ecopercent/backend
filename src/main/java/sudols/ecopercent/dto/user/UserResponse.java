package sudols.ecopercent.dto.user;

import lombok.*;

import java.sql.Blob;

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
