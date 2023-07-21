package sudols.ecopercent.exception;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sudols.ecopercent.common.ErrorCode;
import sudols.ecopercent.dto.ExceptionResponse;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CustomMethodArgumentNotValidException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final ErrorCode errorCode = ErrorCode.INVALID_REQUEST;

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : allErrors) {
            errorCode.setDetail(error.getDefaultMessage());
            log.error("error: {}", error);
            log.error("error: {}", Arrays.toString(error.getCodes()));
        }
        return ResponseEntity
                .status(errorCode.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ExceptionResponse(errorCode));
    }
}
