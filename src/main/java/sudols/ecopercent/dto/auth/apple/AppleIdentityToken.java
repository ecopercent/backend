package sudols.ecopercent.dto.auth.apple;

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
