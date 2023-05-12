package sudols.ecopercent.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String token) {
        super("유효하지 않은 토큰: " + token);
    }
}
