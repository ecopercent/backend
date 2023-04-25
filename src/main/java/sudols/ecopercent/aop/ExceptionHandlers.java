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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<?> handleUserNotExistsException(UserNotExistsException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ItemCategoryNotExistsException.class)
    public ResponseEntity<?> handleItemCategoryNotExistsException(ItemCategoryNotExistsException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ItemNotExistsException.class)
    public ResponseEntity<?> handleItemNotExistsException(ItemNotExistsException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(UserNotItemOwnedException.class)
    public ResponseEntity<?> handleUserNotItemOwnedException(UserNotItemOwnedException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(TitleItemNotFoundException.class)
    public ResponseEntity<?> handleTitleItemNotFoundException(TitleItemNotFoundException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(CategoryMismatchException.class)
    public ResponseEntity<?> handleCategoryMismatchException(CategoryMismatchException e) {
        log.debug("Handling exception: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
