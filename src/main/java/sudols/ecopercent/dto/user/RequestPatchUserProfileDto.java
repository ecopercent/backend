package sudols.ecopercent.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sudols.ecopercent.domain.User;

@Data
public class RequestPatchUserProfileDto {

    private String nickname;

    private String profileImage;

    private String profileMessage;
}
