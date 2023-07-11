package sudols.ecopercent.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheTypeExpireAfterWrite {

    public static Integer refreshToken;

    @Value("${jwt.refresh-token-expiry-date}")
    public void setRefreshToken(Integer refreshToken) {
        CacheTypeExpireAfterWrite.refreshToken = refreshToken;
    }
}
