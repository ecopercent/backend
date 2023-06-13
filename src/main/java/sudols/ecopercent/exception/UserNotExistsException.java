package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class UserNotExistsException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserNotExistsException(String email) {
        super(email + " 은 존재하지 않는 유저입니다.");
        this.errorCode = ErrorCode.USER_NOT_EXISTS;
    }
}
