package sudols.ecopercent.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "TOKEN-001", "만료된 토큰"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN.value(), "TOKEN-002", "유효하지 않은 토큰");

    private final int code;
    private final String name;
    private final String description;
    private final String contentType;

    StatusCode(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.contentType = "application/json";
    }
}
