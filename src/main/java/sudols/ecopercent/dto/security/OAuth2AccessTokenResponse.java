package sudols.ecopercent.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuth2AccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
