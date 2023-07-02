package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class TitleItemNotExistsException extends RuntimeException{

    private final ErrorCode errorCode;

    public TitleItemNotExistsException(String category) {
        super("해당 유저에게 " + category + " 타이틀 아이템이 존재하지 않습니다. ");
        this.errorCode = ErrorCode.TITLE_ITEM_NOT_EXISTS;
    }
}
