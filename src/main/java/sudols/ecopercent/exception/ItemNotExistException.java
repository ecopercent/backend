package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemNotExistException extends RuntimeException{
    public ItemNotExistException(Long itemId) {
        super(itemId + "는 아이템은 존재하지 않습니다.");
    }
}
