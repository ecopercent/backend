package sudols.ecopercent.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sudols.ecopercent.domain.User;

@Data
public class CreateUserRequest {

    @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 공백이 아니어야 합니다.")
    @Email
    private String email;

    private String profileMessage;

    private String oAuthProvider;
}
