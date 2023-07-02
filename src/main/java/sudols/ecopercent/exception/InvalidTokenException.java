package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class InvalidTokenException extends RuntimeException{

    private final ErrorCode errorCode;

    public InvalidTokenException(String token) {
        super("유효하지 않은 토큰: " + token);
        this.errorCode = ErrorCode.INVALID_TOKEN;
    }
}
