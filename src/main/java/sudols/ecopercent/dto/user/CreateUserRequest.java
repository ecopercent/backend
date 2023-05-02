package sudols.ecopercent.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
    private String nickname;

    private String profileImage;

    private String profileMessage;

    @JsonProperty("oAuthProvider")
    private String oAuthProvider;

}
