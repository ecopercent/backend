package sudols.ecopercent.service.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.auth.SignupResponse;
import sudols.ecopercent.dto.auth.kakao.KakaoAccountResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.provider.TokenResponseProvider;
import sudols.ecopercent.security.provider.KakaoOAuth2Provider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service {

    private final UserRepository userRepository;
    private final TokenResponseProvider tokenResponseProvider;
    private final KakaoOAuth2Provider kakaoOAuth2Provider;

    public ResponseEntity<?> login(HttpServletResponse response, String referer, String kakaoAccessToken) {
        KakaoAccountResponse.KakaoAccount kakaoUserDetail = kakaoOAuth2Provider.requestUserDetailByAccessToken(kakaoAccessToken);
        String email = kakaoUserDetail.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            SignupResponse signupResponse = tokenResponseProvider.generateSignupAccessTokenAndGetSignupResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
        }
        tokenResponseProvider.generateTokensAndAddCookie(response, referer, optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
