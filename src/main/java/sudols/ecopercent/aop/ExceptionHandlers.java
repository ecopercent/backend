package sudols.ecopercent.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sudols.ecopercent.exception.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<?> handleUserNotExistException(UserNotExistException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ItemCategoryNotExistException.class)
    public ResponseEntity<?> handleItemCategoryNotExistException(ItemCategoryNotExistException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ItemNotExistException.class)
    public ResponseEntity<?> handleItemNotExistException(ItemNotExistException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(UserNotItemOwnedException.class)
    public ResponseEntity<?> handleUserNotItemOwnedException(UserNotItemOwnedException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
