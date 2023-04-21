package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemNotExistsException extends RuntimeException{
    public ItemNotExistsException(Long itemId) {
        super(itemId + "는 아이템은 존재하지 않습니다.");
    }
}
