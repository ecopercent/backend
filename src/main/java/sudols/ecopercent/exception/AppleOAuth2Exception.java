package sudols.ecopercent.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppleOAuth2Exception extends RuntimeException{
    public AppleOAuth2Exception(Exception e) {
        super("Apple OAuth 로그인 중 문제 발생: " + e);
    }
}
