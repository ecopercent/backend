package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.service.oauth2.AppleOAuth2Service;
import sudols.ecopercent.service.oauth2.KakaoOAuth2Service;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class OAuth2Controller {

    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final AppleOAuth2Service appleOAuth2Service;

    @PostMapping("/kakao")
    @ResponseBody
    public ResponseEntity<?> KakaoOAuth2Login(HttpServletRequest request, HttpServletResponse response) {
        return kakaoOAuth2Service.login(request, response);
    }

    @PostMapping("/apple")
    @ResponseBody
    public ResponseEntity<?> AppleOAuth2Login(HttpServletRequest request, HttpServletResponse response) {
        return appleOAuth2Service.login(request, response);
    }
}
