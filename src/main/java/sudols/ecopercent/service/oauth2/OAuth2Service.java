package sudols.ecopercent.service.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface OAuth2Service {

    ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response);
}
