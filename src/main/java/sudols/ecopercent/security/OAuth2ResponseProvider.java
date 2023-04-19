package sudols.ecopercent.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2ResponseProvider {

    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> returnResponseWithEmailForSignup(String email) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("email", email);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    public ResponseEntity<?> generateTokenAndReturnResponseWithCookie(HttpServletResponse response, User user) {
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

    public ResponseEntity<?> generateTokenAndReturnResponseWithBody(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("access", accessToken);
        responseBody.put("refresh", refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
