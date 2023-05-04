package sudols.ecopercent.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String nickname;

    private String email;

    private byte[] profileImage;

    private String profileMessage;

    private String oAuthProvider;
}
