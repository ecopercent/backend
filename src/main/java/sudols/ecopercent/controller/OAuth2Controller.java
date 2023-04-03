package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.dto.security.AccessTokenFromKakaoResponse;
import sudols.ecopercent.dto.security.KakaoAccountResponse;
import sudols.ecopercent.dto.security.UserInfoFromKakaoResponse;
import sudols.ecopercent.service.auth2.KakaoOAuth2Service;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class OAuth2Controller {

    private final KakaoOAuth2Service kakaoOAuth2Service;

    // TODO: 삭제. TEST
    @GetMapping("/kakao")
    @ResponseBody
    public void KakaoLogin(HttpServletResponse response) {
        kakaoOAuth2Service.kakaoLogin(response);
    }

    @GetMapping("/code/kakao")
    @ResponseBody
    public KakaoAccountResponse KakaoCallback(@RequestParam String code) {
        ResponseEntity<AccessTokenFromKakaoResponse> token = kakaoOAuth2Service.requestToken(code).block();
        String accessToken = token.getBody().getAccessToken();
        ResponseEntity<UserInfoFromKakaoResponse> userInfo = kakaoOAuth2Service.getEmailFromKakaoApi(accessToken).block();
        return userInfo.getBody().getKakaoAccountResponse();
    }
}
