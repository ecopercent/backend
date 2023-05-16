package sudols.ecopercent.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import sudols.ecopercent.dto.auth.kakao.KakaoAccountResponse;
import sudols.ecopercent.security.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2Provider {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.kapi-uri}")
    private String kapiUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoAPI;

    public KakaoAccountResponse.KakaoAccount requestUserDetailByAccessToken(String accessToken) {
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
                .map(KakaoAccountResponse::getKakaoAccount)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve user detail"));
    }
}
