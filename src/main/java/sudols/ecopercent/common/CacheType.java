package sudols.ecopercent.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {
    REFRESH_TOKEN_STORE("refreshTokenStore", CacheTypeExpireAfterWrite.refreshToken, 10000),
    ;

    private final String cacheName;

    private final Integer expireAfterWrite;

    private final Integer maximumSize;
}
