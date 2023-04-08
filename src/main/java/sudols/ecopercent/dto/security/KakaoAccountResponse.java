package sudols.ecopercent.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoAccountResponse {

    @JsonProperty("kakao_account")
    private KakaoUserDetail kakaoUserDetail;
}
