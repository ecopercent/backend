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

    @JsonProperty("user")
    private UserI user;

    @JsonProperty("error")
    private String error;

    @Data
    public static class UserI {

        @JsonProperty("email")
        private String email;

        @JsonProperty("name")
        private NameI nameI;

    }

    @Data
    public static class NameI {

        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("lastName")
        private String lastName;

    }

}
