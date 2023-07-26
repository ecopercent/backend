package sudols.ecopercent.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateUserRequest {

    @NotBlank
    @Size(min = 2, max = 8, message = "User nickname can have a minimum of 2 characters and maximum of 35 characters.")
    @Pattern(regexp = "^[a-zA-Zㄱ-힣]+$", message = "User nickname must contain only English and Korean alphabets.")
    private String nickname;

    @Size(max = 35, message = "Profile message can have a maximum of 35 characters.")
    private String profileMessage;

    @NotBlank
    @JsonProperty("oAuthProvider")
    private String oAuthProvider;
}
