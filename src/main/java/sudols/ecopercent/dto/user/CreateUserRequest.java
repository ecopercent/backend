package sudols.ecopercent.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateUserRequest {

    private String nickname;

    private String profileMessage;

    @JsonProperty("oAuthProvider")
    private String oAuthProvider;
}
