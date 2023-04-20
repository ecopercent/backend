package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.service.auth2.KakaoOAuth2Service;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class OAuth2Controller {

    private final KakaoOAuth2Service kakaoOAuth2Service;

    @PostMapping("kakao")
    @ResponseBody
    public ResponseEntity<?> KakaoOAuthLogin(HttpServletRequest request, HttpServletResponse response) {
        return kakaoOAuth2Service.kakaoOAuthLogin(request, response);
    }
}
