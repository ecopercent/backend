package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String email) {
        super(email + " 은 존재하지 않는 유저입니다.");
    }
}
