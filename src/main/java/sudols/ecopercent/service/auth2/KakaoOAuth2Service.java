package sudols.ecopercent.service.auth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.security.KakaoAccountResponse;
import sudols.ecopercent.dto.security.KakaoUserDetail;
import sudols.ecopercent.dto.security.OAuth2AccessTokenResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service implements OAuth2Service {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.authorization-grant-type}")
    private String grantType;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.kauth-uri}")
    private String kauthUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.kapi-uri}")
    private String kapiUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoAPI;

    @Override
    public ResponseEntity<?> kakaoOAuthLogin(HttpServletResponse response, String code) {
        String kakaoAccessToken = requestTokenByCode(code);
        KakaoUserDetail kakaoUserDetail = requestUserDetailByAccessToken(kakaoAccessToken);
        String email = kakaoUserDetail.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            Cookie emailCookie = new Cookie("email", email);
            emailCookie.setPath("/signup");
            response.addCookie(emailCookie);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return jwtTokenProvider.generateTokenAndReturnResponseWithCookie(response, optionalUser.get());
    }

    private String requestTokenByCode(String code) {
        return WebClient.create(kauthUri)
                .post()
                .uri(uriBuilder -> uriBuilder.path(tokenUri)
                        .queryParam("grant_type", grantType)
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .toEntity(OAuth2AccessTokenResponse.class)
                .blockOptional()
                .map(ResponseEntity::getBody)
                .map(OAuth2AccessTokenResponse::getAccessToken)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve access token"));
    }

    private KakaoUserDetail requestUserDetailByAccessToken(String accessToken) {
        WebClient client = WebClient.builder()
                .baseUrl(kapiUri)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path(userInfoAPI)
                        .build())
                .retrieve()
                .toEntity(KakaoAccountResponse.class)
                .blockOptional()
                .map(ResponseEntity::getBody)
                .map(KakaoAccountResponse::getKakaoUserDetail)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve user detail"));
    }
}
