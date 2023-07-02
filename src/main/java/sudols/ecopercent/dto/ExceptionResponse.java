package sudols.ecopercent.dto;

import lombok.Getter;
import lombok.Setter;
import sudols.ecopercent.common.ErrorCode;

@Getter
@Setter
public class ExceptionResponse {

    private final String code;

    private final String message;

    public ExceptionResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
