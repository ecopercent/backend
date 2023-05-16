package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.auth.SignupResponse;
import sudols.ecopercent.dto.auth.kakao.KakaoAccountResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.security.auth.KakaoOAuth2Provider;
import sudols.ecopercent.security.TokenResponseProvider;

import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenResponseProvider tokenResponseProvider;
    private final KakaoOAuth2Provider kakaoOAuth2Provider;

    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response) {
        String kakaoAccessToken = jwtTokenProvider.getTokenFromRequest(request);
        KakaoAccountResponse.KakaoAccount kakaoUserDetail = kakaoOAuth2Provider.requestUserDetailByAccessToken(kakaoAccessToken);
        String email = kakaoUserDetail.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            SignupResponse signupResponse = tokenResponseProvider.generateAccessTokenAndGetSignupResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
        }
        try {
            final String referer = request.getHeader("Referer");
            final String HostOfReferer = new URL(referer).getHost();
            tokenResponseProvider.generateTokensAndAddCookieForWeb(response, optionalUser.get(), HostOfReferer);
        } catch (Exception e) {
            tokenResponseProvider.generateTokensAndAddCookieForIos(response, optionalUser.get());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
