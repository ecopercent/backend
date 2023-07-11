package sudols.ecopercent.security.provider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.auth.SignupResponse;
import sudols.ecopercent.dto.auth.apple.AppleSignInResponse;
import sudols.ecopercent.service.CacheService;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class TokenResponseProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final CacheService cacheService;

    public void generateSignupAccessTokenAndAddCookie(HttpServletResponse response, String referer, String email) {
        String access = jwtTokenProvider.generateAccessToken(email, "SIGNUP");
        Cookie accessTokenCookie = new Cookie("access", access);
        try {
            final String domain = new URL(referer).getHost();
            accessTokenCookie.setDomain(domain);
        } catch (Exception ignore) {
        }
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);
    }

    public SignupResponse generateSignupAccessTokenAndGetSignupResponse(String email) {
        String accessToken = jwtTokenProvider.generateAccessToken(email, "SIGNUP");
        return SignupResponse.builder()
                .access(accessToken)
                .build();
    }

    public void generateTokensAndAddCookie(HttpServletResponse response, String referer, User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), "USER");
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        cacheService.saveRefreshToken(user.getEmail(), refreshToken);

        Cookie accessTokenCookie = new Cookie("access", accessToken);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        try {
            final String domain = new URL(referer).getHost();
            accessTokenCookie.setDomain(domain);
            refreshTokenCookie.setDomain(domain);
        } catch (Exception ignore) {
        }

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    public AppleSignInResponse generateTokensAndGetTokenResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), "USER");
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        cacheService.saveRefreshToken(user.getEmail(),refreshToken);
        return AppleSignInResponse.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();
    }

    public Cookie generateUserAccessTokenCookie(String referer, String email) {
        String accessToken = jwtTokenProvider.generateAccessToken(email, "USER");
        Cookie accessTokenCookie = new Cookie("access", accessToken);
        accessTokenCookie.setPath("/");
        try {
            final String domain = new URL(referer).getHost();
            accessTokenCookie.setDomain(domain);
        } catch (Exception ignore) {
        }
        return accessTokenCookie;
    }

    public Cookie generateExpiredRefreshTokenCookie(String email, String referer) {
        String refreshToken = jwtTokenProvider.generateExpiredRefreshToken(email);
        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setPath("/");
        try {
            final String domain = new URL(referer).getHost();
            refreshTokenCookie.setDomain(domain);
        } catch (Exception ignore) {
        }
        refreshTokenCookie.setHttpOnly(true);
        return refreshTokenCookie;
    }
}
