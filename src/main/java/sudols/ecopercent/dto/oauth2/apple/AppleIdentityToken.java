package sudols.ecopercent.dto.oauth2.apple;

import lombok.Data;

@Data
public class AppleIdentityToken {
    private Header header;

    @Data
    public static class Header {
        private String kid;
        private String alg;
    }
}
