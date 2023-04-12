package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.domain.User;

@Slf4j
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super(email + " 은 이미 존재하는 유저입니다."); // TODO: 로그로 변경
    }
}
