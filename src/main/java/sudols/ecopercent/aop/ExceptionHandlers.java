package sudols.ecopercent.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sudols.ecopercent.exception.UserAlreadyExistsException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
