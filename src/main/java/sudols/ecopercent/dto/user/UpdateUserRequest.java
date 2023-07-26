package sudols.ecopercent.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    @NotBlank
    @Size(min = 2, max = 8, message = "User nickname can have a minimum of 2 characters and maximum of 35 characters.")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "User nickname must contain only English and Korean alphabets.")
    private String nickname;

    @Size(max = 35, message = "Profile message can have a maximum of 35 characters.")
    private String profileMessage;
}
