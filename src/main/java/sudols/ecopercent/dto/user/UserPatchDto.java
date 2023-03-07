package sudols.ecopercent.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sudols.ecopercent.domain.User;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPatchDto {

    private Long userId;

    @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 공백이 아니어야 합니다.")
    @Email
    private String email;

    private String profileImage;

    private String profileMessage;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .nickname(nickname)
                .email(email)
                .profileImage(profileImage)
                .profileMessage(profileMessage)
                .build();
    }
}
