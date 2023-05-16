package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String token) {
        super("만료된 토큰: " + token);
    }
}
