package sudols.ecopercent.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {
    REFRESH_TOKEN_STORE("refreshTokenStore"),
    ;

    private final String cacheName;

    private final int expireAfterWrite;

    private final int maximumSize;

    CacheType(String cacheName) {
        this.cacheName = cacheName;
        this.expireAfterWrite = RefreshCacheConfig.expireAfterWrite;
        this.maximumSize = RefreshCacheConfig.maximumSize;
    }

    static class RefreshCacheConfig {

        static final int maximumSize = 10000;
        //        @Value("${jwt.refresh-token-expiry-date}")
        static final int expireAfterWrite = 60000;
    }

}
