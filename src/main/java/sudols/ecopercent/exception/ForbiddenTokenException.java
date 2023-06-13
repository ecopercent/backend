package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class ForbiddenTokenException extends RuntimeException{

    private final ErrorCode errorCode;

    public ForbiddenTokenException(String token) {
        super("허용되지 않은 토큰: " + token);
        this.errorCode = ErrorCode.FORBIDDEN_TOKEN;
    }
}
