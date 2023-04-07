package sudols.ecopercent.service.auth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuth2Service {

    void handleOAuth2Callback(HttpServletRequest request, HttpServletResponse response, String code);
}
