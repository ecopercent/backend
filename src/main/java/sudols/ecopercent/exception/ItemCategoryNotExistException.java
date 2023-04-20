package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemCategoryNotExistException extends RuntimeException {
    public ItemCategoryNotExistException(String category) {
        super(category + "는 존재하지 않는 카테고리입니다.");
    }
}
