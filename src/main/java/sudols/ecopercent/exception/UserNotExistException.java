package sudols.ecopercent.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String email) {
        super(email + " 은 존재하지 않는 유저입니다.");
    }
}
