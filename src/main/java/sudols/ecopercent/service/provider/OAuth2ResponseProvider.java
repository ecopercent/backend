package sudols.ecopercent.service.provider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.SignupResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleSignInResponse;
import sudols.ecopercent.security.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class OAuth2ResponseProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public void generateAccessTokenAndAddCookieForWeb(HttpServletResponse response, String email, String domain) {
        String access = jwtTokenProvider.generateAccessToken(email);
        Cookie accessTokenCookie = new Cookie("access", access);
        accessTokenCookie.setDomain(domain);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);
    }

    public void generateTokensAndAddCookieForWeb(HttpServletResponse response, User user, String domain) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

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

    public void generateAccessTokenAndAddCookieForIOS(HttpServletResponse response, String email) {
        String access = jwtTokenProvider.generateAccessToken(email);
        Cookie accessTokenCookie = new Cookie("access", access);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);
    }

    public void generateTokensAndAddCookieForIos(HttpServletResponse response, User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        Cookie accessTokenCookie = new Cookie("access", accessToken);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    public SignupResponse generateAccessTokenAndGetSignupResponse(String email) {
        String accessToken = jwtTokenProvider.generateAccessToken(email);
        return SignupResponse.builder()
                .access(accessToken)
                .build();
    }

    public AppleSignInResponse generateTokenAndGetTokenResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        return AppleSignInResponse.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();
    }
}
