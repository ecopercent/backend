package sudols.ecopercent.service.auth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.security.KakaoAccountResponse;
import sudols.ecopercent.dto.security.KakaoUserDetail;
import sudols.ecopercent.dto.security.OAuth2AccessTokenResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;

import java.io.IOException;
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
    public void login(HttpServletResponse response) {
        String responseType = "code";
        String authUrl = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=%s", clientId, redirectUri, responseType);
        try {
            response.sendRedirect(authUrl);
        } catch (IOException e) {
            throw new RuntimeException("Error redirecting to Kakao authorization screen", e);
        }
    }

    @Override
    public void handleOAuth2Callback(HttpServletRequest request, HttpServletResponse response, String code) {
        String kakaoAccessToken = requestTokenByCode(code)
                .blockOptional()
                .map(ResponseEntity::getBody)
                .map(OAuth2AccessTokenResponse::getAccessToken)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve access token"));

        KakaoUserDetail kakaoUserDetail = requestEmailByAccessToken(kakaoAccessToken)
                .blockOptional()
                .map(ResponseEntity::getBody)
                .map(KakaoAccountResponse::getKakaoUserDetail)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve user detail"));

        String email = kakaoUserDetail.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            jwtTokenProvider.generateTokenAndRedirectHomeWithCookie(response, email);
        } else {
            Cookie emailCookie = new Cookie("email", email);
            emailCookie.setPath("/signup");
            response.addCookie(emailCookie);
            try {
                response.sendRedirect("http://localhost:3000/signup");
            } catch (IOException e) {
                System.out.println("Failed redirection: " + e); // TODO: 구현. 예외처리
            }
        }
    }

    private Mono<ResponseEntity<OAuth2AccessTokenResponse>> requestTokenByCode(String code) {
        return WebClient.create(kauthUri)
                .post()
                .uri(uriBuilder -> uriBuilder.path(tokenUri)
                        .queryParam("grant_type", grantType)
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .toEntity(OAuth2AccessTokenResponse.class);
    }

    private Mono<ResponseEntity<KakaoAccountResponse>> requestEmailByAccessToken(String accessToken) {
        WebClient client = WebClient.builder()
                .baseUrl(kapiUri)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path(userInfoAPI)
                        .build())
                .retrieve()
                .toEntity(KakaoAccountResponse.class);
    }
}
