package sudols.ecopercent.dto;

import lombok.Getter;
import lombok.Setter;
import sudols.ecopercent.common.ErrorCode;

@Getter
@Setter
public class ErrorResponse {

    private final Integer status;

    private final String code;

    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
