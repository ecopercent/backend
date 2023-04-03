package sudols.ecopercent.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfoFromKakaoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccountResponse kakaoAccountResponse;
}
