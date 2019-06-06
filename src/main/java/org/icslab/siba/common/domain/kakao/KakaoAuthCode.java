package org.icslab.siba.common.domain.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoAuthCode {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token-type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    private String scope;
}
