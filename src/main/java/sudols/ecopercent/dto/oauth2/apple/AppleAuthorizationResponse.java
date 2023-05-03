package sudols.ecopercent.dto.oauth2.apple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class AppleAuthorizationResponse {

    private String code;

    private String id_token;

    private String state;
}
