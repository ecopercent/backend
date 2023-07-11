package sudols.ecopercent.service;


import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.exception.ForbiddenTokenException;
import sudols.ecopercent.security.provider.JwtTokenProvider;
import sudols.ecopercent.security.provider.TokenResponseProvider;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CacheService cacheService;
    private final TokenResponseProvider tokenResponseProvider;

    public Cookie reissueUserAccessTokenCookie(final String referer, String refresh) {
        if (refresh == null) {
            throw new ForbiddenTokenException(null);
        }
        jwtTokenProvider.validateToken(refresh);
        String email = jwtTokenProvider.getEmailFromToken(refresh);
        if (!refresh.equals(cacheService.getRefreshToken(email))) {
            throw new ForbiddenTokenException(refresh);
        }
        return tokenResponseProvider.generateUserAccessTokenCookie(referer, email);
    }

    public Cookie revokeRefreshTokenAndReturnExpiredRefreshCookie(String referer, String refresh) {
        try {
            String email = jwtTokenProvider.getEmailFromToken(refresh);
            cacheService.deleteRefreshToken(email);
            return tokenResponseProvider.generateExpiredRefreshTokenCookie(referer, email);
        } catch (Exception e) {
            return null;
        }
    }
}
