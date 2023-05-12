package sudols.ecopercent.service.provider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.auth.SignupResponse;
import sudols.ecopercent.dto.auth.apple.AppleSignInResponse;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.service.CacheService;

@Component
@RequiredArgsConstructor
public class OAuth2ResponseProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final CacheService cacheService;

    public void generateAccessTokenAndAddCookieForWeb(HttpServletResponse response, String email, String domain) {
        String access = jwtTokenProvider.generateAccessToken(email);
        Cookie accessTokenCookie = new Cookie("access", access);
        accessTokenCookie.setDomain(domain);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);
    }

    public void generateAccessTokenAndAddCookieForIOS(HttpServletResponse response, String email) {
        String access = jwtTokenProvider.generateAccessToken(email);
        Cookie accessTokenCookie = new Cookie("access", access);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);
    }

    public SignupResponse generateAccessTokenAndGetSignupResponse(String email) {
        String accessToken = jwtTokenProvider.generateAccessToken(email);
        return SignupResponse.builder()
                .access(accessToken)
                .build();
    }

    public void generateTokensAndAddCookieForWeb(HttpServletResponse response, User user, String domain) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        System.out.println("refresh token: " + refreshToken);
        cacheService.saveRefreshToken(user.getEmail(), refreshToken);

        Cookie accessTokenCookie = new Cookie("access", accessToken);
        accessTokenCookie.setDomain(domain);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setDomain(domain);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    public void generateTokensAndAddCookieForIos(HttpServletResponse response, User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        System.out.println("refresh token: " + refreshToken);
        cacheService.saveRefreshToken(user.getEmail(),refreshToken);

        Cookie accessTokenCookie = new Cookie("access", accessToken);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }


    public AppleSignInResponse generateTokenAndGetTokenResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        System.out.println("refresh token: " + refreshToken);
        cacheService.saveRefreshToken(user.getEmail(),refreshToken);
        return AppleSignInResponse.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();
    }
}
