package sudols.ecopercent.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sudols.ecopercent.common.ErrorCode;

@Slf4j
@Getter
public class CategoryMismatchException extends RuntimeException{

    private final ErrorCode errorCode;

    public CategoryMismatchException(Long itemId) {
        super(itemId + " 의 카테고리와 요청하신 카테고리가 다릅니다.");
        this.errorCode = ErrorCode.CATEGORY_MISMATCH;
    }
}
