package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserAlreadyExistsException(String email) {
        super(email + " 은 이미 존재하는 유저입니다.");
        this.errorCode = ErrorCode.USER_ALREADY_EXISTS;
    }
}
