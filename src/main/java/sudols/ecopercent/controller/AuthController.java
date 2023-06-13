package sudols.ecopercent.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sudols.ecopercent.dto.auth.apple.AppleAuthorizationResponse;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.service.AppleOAuth2IosService;
import sudols.ecopercent.service.AppleOAuth2WebService;
import sudols.ecopercent.service.KakaoOAuth2Service;
import sudols.ecopercent.service.TokenService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final AppleOAuth2IosService appleOAuth2IosService;
    private final AppleOAuth2WebService appleOAuth2WebService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login/oauth2/kakao")
    public ResponseEntity<?> kakaoOAuth2Login(HttpServletRequest request,
                                              HttpServletResponse response) {
        String kakaoAccessToken = jwtTokenProvider.getTokenFromRequest(request);
        final String referer = request.getHeader("Referer");
        return kakaoOAuth2Service.login(response, kakaoAccessToken, referer);
    }

    @PostMapping("/login/oauth2/apple/ios")
    public ResponseEntity<?> appleOAuth2IosLogin(HttpServletRequest request) {
        return appleOAuth2IosService.login(request);
    }

    @PostMapping("/login/oauth2/apple/web")
    public ResponseEntity<?> appleOAuth2LoginWeb(HttpServletResponse response,
                                                 @ModelAttribute AppleAuthorizationResponse appleAuthorizationResponse) {
        return appleOAuth2WebService.login(response, appleAuthorizationResponse);
    }

    @PostMapping("/token/access")
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, @CookieValue("refresh") String refresh) {
        String referer = request.getHeader("Referer");
        Cookie cookie = tokenService.reissueUserAccessTokenCookie(referer, refresh);
        response.addCookie(cookie);
    }

    @PostMapping("/signout")
    public void logout(HttpServletRequest request, HttpServletResponse response, @CookieValue("refresh") String refresh) {
        String referer = request.getHeader("Referer");
        Cookie expiredRefreshCookie = tokenService.revokeRefreshTokenAndReturnExpiredRefreshCookie(referer, refresh);
        if (expiredRefreshCookie != null) {
            response.addCookie(expiredRefreshCookie);
        }
    }
}
