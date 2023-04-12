package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sudols.ecopercent.service.auth2.KakaoOAuth2Service;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class OAuth2Controller {

    private final KakaoOAuth2Service kakaoOAuth2Service;

    @GetMapping("/code/kakao")
    @ResponseBody
    public void KakaoOAuthCallback(@RequestParam String code) {
        System.out.println("code: " + code);
    }

    @GetMapping("kakao")
    @ResponseBody
    public ResponseEntity<?> KakaoOAuthLogin(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getHeader("x-authorization-code");
        System.out.println("code: " + code);
        return kakaoOAuth2Service.kakaoOAuthLogin(response, code);
    }
}
