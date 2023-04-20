package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String email) {
        super(email + " 은 이미 존재하는 유저입니다.");
    }
}
