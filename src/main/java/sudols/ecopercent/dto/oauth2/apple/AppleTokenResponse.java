package sudols.ecopercent.dto.oauth2.apple;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppleTokenResponse {

    private String access;
    private String refresh;
}
