package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class ItemCategoryNotExistsException extends RuntimeException {

    private final ErrorCode errorCode;

    public ItemCategoryNotExistsException(String category) {
        super(category + "는 존재하지 않는 카테고리입니다.");
        this.errorCode = ErrorCode.ITEM_CATEGORY_NOT_EXISTS;
    }
}
