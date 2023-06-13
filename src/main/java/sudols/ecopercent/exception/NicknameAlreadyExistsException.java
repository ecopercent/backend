package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class NicknameAlreadyExistsException extends RuntimeException{

    private final ErrorCode errorCode;

    public NicknameAlreadyExistsException(String nickname) {
        super(nickname + ": 이미 존재하는 닉네임입니다.");
        this.errorCode = ErrorCode.NICKNAME_ALREADY_EXISTS;
    }
}
