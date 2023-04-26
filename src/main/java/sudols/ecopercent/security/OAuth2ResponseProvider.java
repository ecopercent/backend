package sudols.ecopercent.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.EmailResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;

@Component
@RequiredArgsConstructor
public class OAuth2ResponseProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public void addEmailCookie(HttpServletResponse response, String email) {
        Cookie emailCookie = new Cookie("email", email);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);
    }

    public EmailResponse getEmailResponse(String email) {
        return EmailResponse.builder()
                .email(email)
                .build();
    }

    public void generateTokenAndAddTokenCookie(HttpServletResponse response, User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        Cookie accessTokenCookie = new Cookie("access", accessToken);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        Cookie useridCookie = new Cookie("userid", user.getId().toString());
        useridCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        response.addCookie(useridCookie);
    }

    public AppleTokenResponse generateTokenAndGetTokenResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        return AppleTokenResponse.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();
    }
}
