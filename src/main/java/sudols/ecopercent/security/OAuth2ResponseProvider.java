package sudols.ecopercent.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.EmailResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;

@Component
@RequiredArgsConstructor
public class OAuth2ResponseProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public EmailResponse returnEmailResponse(String email) {
        return EmailResponse.builder()
                .email(email)
                .build();
    }

    public void generateTokenAndAddCookie(HttpServletResponse response, User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        Cookie accessTokenCookie = new Cookie("access", accessToken);
        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        Cookie useridCookie = new Cookie("userid", user.getId().toString());

        accessTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        useridCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        response.addCookie(useridCookie);
        ResponseEntity.status(HttpStatus.OK).build();
    }

    public AppleTokenResponse generateTokenReturnTokenResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return AppleTokenResponse.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();
    }
}
