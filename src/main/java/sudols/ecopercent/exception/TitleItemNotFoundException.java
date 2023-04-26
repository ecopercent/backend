package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TitleItemNotFoundException extends RuntimeException{
    public TitleItemNotFoundException(String category) {
        super("해당 유저에게 " + category + " 타이틀 아이템이 존재하지 않습니다. ");
    }
}
