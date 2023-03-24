package sudols.ecopercent.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sudols.ecopercent.domain.User;

@Data
public class RequestPatchUserProfileDto {

    @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
    private String nickname;

    private String profileImage;

    private String profileMessage;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .profileImage(profileImage)
                .profileMessage(profileMessage)
                .build();
    }
}
