package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotItemOwnedException extends RuntimeException{
    public UserNotItemOwnedException(Long itemId) {
        super(itemId + " 을 수정할 권한이 없습니다.");
    }
}
