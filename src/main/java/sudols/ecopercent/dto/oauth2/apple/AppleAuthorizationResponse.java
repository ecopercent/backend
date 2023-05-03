package sudols.ecopercent.dto.oauth2.apple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AppleAuthorizationResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("state")
    private String state;
}
