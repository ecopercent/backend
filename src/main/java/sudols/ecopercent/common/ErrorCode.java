package sudols.ecopercent.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "TOKEN-001", "Expired token"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN.value(), "TOKEN-002", "Invalid token");

    private final Integer status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
