package sudols.ecopercent.dto.auth.apple;

import lombok.Data;

@Data
public class AppleAuthorizationResponse {

    private String code;

    private String id_token;

    private String state;
}
