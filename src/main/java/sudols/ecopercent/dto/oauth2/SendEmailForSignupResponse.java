package sudols.ecopercent.dto.oauth2;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailForSignupResponse {

    String email;
}
