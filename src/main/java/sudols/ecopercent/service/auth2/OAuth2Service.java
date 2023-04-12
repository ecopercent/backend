package sudols.ecopercent.service.auth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface OAuth2Service {

    ResponseEntity<?> kakaoOAuthLogin(HttpServletResponse response, String code);
}
