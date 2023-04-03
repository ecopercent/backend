package sudols.ecopercent.service.auth2;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import sudols.ecopercent.dto.security.AccessTokenFromKakaoResponse;
import sudols.ecopercent.dto.security.UserInfoFromKakaoResponse;

public interface OAuth2Service {

    void kakaoLogin(HttpServletResponse response);

    Mono<ResponseEntity<AccessTokenFromKakaoResponse>> requestToken(String code);

    Mono<ResponseEntity<UserInfoFromKakaoResponse>> getEmailFromKakaoApi(String accessToken);
}
