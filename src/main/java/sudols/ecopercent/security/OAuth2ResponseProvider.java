package sudols.ecopercent.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.SendEmailForSignupResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleSignInResponse;

@Component
@RequiredArgsConstructor
public class OAuth2ResponseProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<SendEmailForSignupResponse> returnResponseWithEmailForSignup(String email) {
        SendEmailForSignupResponse sendEmailForSignupResponse = SendEmailForSignupResponse.builder()
                .email(email)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(sendEmailForSignupResponse);
    }

    public ResponseEntity<Void> generateTokenAndReturnResponseWithCookie(HttpServletResponse response, User user) {
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
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<AppleSignInResponse> generateTokenAndReturnResponseWithBody(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        AppleSignInResponse appleSignInResponse = AppleSignInResponse.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(appleSignInResponse);
    }
}
