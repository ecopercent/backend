package sudols.ecopercent.exception;

public class ForbiddenTokenException extends RuntimeException{
    public ForbiddenTokenException(String token) {
        super("허용되지 않은 토큰: " + token);
    }
}
