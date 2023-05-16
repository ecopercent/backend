package sudols.ecopercent.service;


import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.exception.ExpiredTokenException;
import sudols.ecopercent.exception.ForbiddenTokenException;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.security.TokenResponseProvider;

import java.net.URL;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CacheService cacheService;
    private final TokenResponseProvider tokenResponseProvider;

    public Cookie reissueUserAccessToken(final String referer, String refresh) {
        if (refresh == null) {
            throw new ForbiddenTokenException(null);
        }
        jwtTokenProvider.validateToken(refresh);
        String email = jwtTokenProvider.getEmailFromToken(refresh);
        if (!refresh.equals(cacheService.getRefreshToken(email))) {
            throw new ForbiddenTokenException(refresh);
        }
        try {
            final String HostOfReferer = new URL(referer).getHost();
            return tokenResponseProvider.generateUserAccessTokenCookieForWeb(email, HostOfReferer);
        } catch (Exception e) {
            return tokenResponseProvider.generateUserAccessTokenCookieForIos(email);
        }
    }

    public void revokeRefreshToken() {

    }
}
