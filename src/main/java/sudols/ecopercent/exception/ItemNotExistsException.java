package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class ItemNotExistsException extends RuntimeException{

    private final ErrorCode errorCode;

    public ItemNotExistsException(Long itemId) {
        super(itemId + "는 아이템은 존재하지 않습니다.");
        this.errorCode = ErrorCode.ITEM_NOT_EXISTS;
    }
}
