package sudols.ecopercent.dto.auth.apple;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppleSignInResponse {

    private String access;
    private String refresh;
}
