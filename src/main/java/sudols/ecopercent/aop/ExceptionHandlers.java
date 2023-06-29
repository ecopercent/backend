package sudols.ecopercent.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sudols.ecopercent.common.ErrorCode;
import sudols.ecopercent.dto.ExceptionResponse;
import sudols.ecopercent.exception.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    private ResponseEntity<ExceptionResponse> buildResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ExceptionResponse(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<?> handleUserNotExistsException(UserNotExistsException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(UserNotItemOwnedException.class)
    public ResponseEntity<?> handleUserNotItemOwnedException(UserNotItemOwnedException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(NicknameAlreadyExistsException.class)
    public ResponseEntity<?> handleNicknameAlreadyExistsException(NicknameAlreadyExistsException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(ItemNotExistsException.class)
    public ResponseEntity<?> handleItemNotExistsException(ItemNotExistsException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(ItemCategoryNotExistsException.class)
    public ResponseEntity<?> handleItemCategoryNotExistsException(ItemCategoryNotExistsException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(TitleItemNotExistsException.class)
    public ResponseEntity<?> handleTitleItemNotFoundException(TitleItemNotExistsException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(CategoryMismatchException.class)
    public ResponseEntity<?> handleCategoryMismatchException(CategoryMismatchException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(ForbiddenTokenException.class)
    public ResponseEntity<?> handleForbiddenTokenException(ForbiddenTokenException e) {
        log.debug("Handling exception: " + e);
        return buildResponseEntity(e.getErrorCode());
    }
}
