package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class UserNotItemOwnedException extends RuntimeException{

    private final ErrorCode errorCode;

    public UserNotItemOwnedException(Long itemId) {
        super(itemId + " 을 수정할 권한이 없습니다.");
        this.errorCode = ErrorCode.USER_NOT_ITEM_OWNED;
    }
}
