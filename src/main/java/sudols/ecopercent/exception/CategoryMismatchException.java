package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryMismatchException extends RuntimeException{
    public CategoryMismatchException(Long itemId) {
        super(itemId + " 의 카테고리와 요청하신 카테고리가 다릅니다.");
    }
}
