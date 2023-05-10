package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.SignupResponse;
import sudols.ecopercent.dto.oauth2.kakao.KakaoAccountResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.service.provider.KakaoOAuth2Provider;
import sudols.ecopercent.service.provider.OAuth2ResponseProvider;

import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ResponseProvider oAuth2ResponseProvider;
    private final KakaoOAuth2Provider kakaoOAuth2Provider;

    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response) {
        String kakaoAccessToken = jwtTokenProvider.getTokenFromRequest(request);
        KakaoAccountResponse.KakaoAccount kakaoUserDetail = kakaoOAuth2Provider.requestUserDetailByAccessToken(kakaoAccessToken);
        String email = kakaoUserDetail.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            SignupResponse signupResponse = oAuth2ResponseProvider.generateAccessTokenAndGetSignupResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
        }
        try {
            final String referer = request.getHeader("Referer");
            final String HostOfReferer = new URL(referer).getHost();
            oAuth2ResponseProvider.generateTokensAndAddCookieForWeb(response, optionalUser.get(), HostOfReferer);
        } catch (Exception e) {
            oAuth2ResponseProvider.generateTokensAndAddCookieForIos(response, optionalUser.get());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
