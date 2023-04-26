package sudols.ecopercent.service.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.EmailResponse;
import sudols.ecopercent.dto.oauth2.kakao.KakaoAccountResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.security.OAuth2ResponseProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service implements OAuth2Service {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ResponseProvider oAuth2ResponseProvider;

    @Value("${kakao.kapi-uri}")
    private String kapiUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoAPI;

    @Override
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response) {
        String kakaoAccessToken = jwtTokenProvider.getTokenFromRequest(request);
        KakaoAccountResponse.KakaoAccount kakaoUserDetail = requestUserDetailByAccessToken(kakaoAccessToken);
        String email = kakaoUserDetail.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            EmailResponse emailResponse = oAuth2ResponseProvider.returnEmailResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emailResponse);
        }
        oAuth2ResponseProvider.generateTokenAndAddCookie(response, optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private KakaoAccountResponse.KakaoAccount requestUserDetailByAccessToken(String accessToken) {
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
