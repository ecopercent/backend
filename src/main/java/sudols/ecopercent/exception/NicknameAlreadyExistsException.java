package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NicknameAlreadyExistsException extends RuntimeException{
    public NicknameAlreadyExistsException(String nickname) {
        super(nickname + ": 이미 존재하는 닉네임입니다.");
    }
}
