package org.icslab.sibadev.common.domain.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoDTO {

    private String id;

    public void setId(Long id){
        this.id=id.toString();
    }

    @JsonProperty("has_signed_up")
    private boolean hasSignedUp;

    private KakaoProperties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
}


