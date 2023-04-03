package sudols.ecopercent.service.auth2;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sudols.ecopercent.dto.security.AccessTokenFromKakaoResponse;
import sudols.ecopercent.dto.security.UserInfoFromKakaoResponse;

import java.io.IOException;

@Service
public class KakaoOAuth2Service implements OAuth2Service {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.kauth-uri}")
    private String kauthUri;

    @Value("${kakao.authorization-grant-type}")
    private String grantType;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.kapi-uri}")
    private String kapiUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoAPI;

    @Override
    public void kakaoLogin(HttpServletResponse response) {
        String responseType = "code";
        String authUrl = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=%s", clientId, redirectUri, responseType);
        try {
            response.sendRedirect(authUrl);
        } catch (IOException e) {
            throw new RuntimeException("Error redirecting to Kakao authorization screen", e);
        }
    }

    @Override
    public Mono<ResponseEntity<AccessTokenFromKakaoResponse>> requestToken(String code) {
        return WebClient.create(kauthUri)
                .post()
                .uri(uriBuilder -> uriBuilder.path(tokenUri)
                        .queryParam("grant_type", grantType)
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .toEntity(AccessTokenFromKakaoResponse.class);
    }

    @Override
    public Mono<ResponseEntity<UserInfoFromKakaoResponse>> getEmailFromKakaoApi(String accessToken) {
        WebClient client = WebClient.builder()
                .baseUrl(kapiUri)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path(userInfoAPI)
                        .build())
                .retrieve()
                .toEntity(UserInfoFromKakaoResponse.class);
    }
}
