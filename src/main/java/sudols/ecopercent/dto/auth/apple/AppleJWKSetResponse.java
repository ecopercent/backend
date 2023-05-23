package sudols.ecopercent.dto.auth.apple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AppleJWKSetResponse {

    @JsonProperty("keys")
    List<Key> keys;

    @Data
    public static class Key {

        @JsonProperty("kty")
        private String kty;

        @JsonProperty("kid")
        private String kid;

        @JsonProperty("use")
        private String use;

        @JsonProperty("alg")
        private String alg;

        @JsonProperty("n")
        private String n;

        @JsonProperty("e")
        private String e;
    }
}
