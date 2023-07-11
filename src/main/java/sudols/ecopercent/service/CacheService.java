package sudols.ecopercent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    @Cacheable(cacheNames = "refreshTokenStore", key = "#email")
    public String saveRefreshTokenToCache(String email, String refresh) {
        log.debug("refreshTokenStore 캐시 저장: " + email);
        return refresh;
    }

    @Cacheable(cacheNames = "refreshTokenStore", key = "#email")
    public String getRefreshTokenFromCache(String email) {
        log.debug("refreshTokenStore 캐시 반환: " + email);
        return null;
    }

    @CacheEvict(value = "refreshTokenStore", key = "#email")
    public void deleteRefreshTokenFromCache(String email) {
        log.debug("refreshTokenStore 캐시 삭제: " + email);
    }
}
