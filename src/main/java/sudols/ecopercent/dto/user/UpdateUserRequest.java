package sudols.ecopercent.dto.user;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    private String nickname;

    private String profileMessage;
}
