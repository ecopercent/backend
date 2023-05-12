package sudols.ecopercent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final Cache refreshTokenCache;

    @Autowired
    public CacheService(CacheManager cacheManager) {
        this.refreshTokenCache = cacheManager.getCache("refreshTokenStore");
    }

    public void saveRefreshToken(String email, String refresh) {
        refreshTokenCache.put(email, refresh);
    }

    public String getRefreshToken(String email) {
        Cache.ValueWrapper valueWrapper = refreshTokenCache.get(email);
        return valueWrapper == null ? null : (String) valueWrapper.get();
    }

    public void deleteRefreshToken(String email) {
        refreshTokenCache.evict(email);
    }
}
