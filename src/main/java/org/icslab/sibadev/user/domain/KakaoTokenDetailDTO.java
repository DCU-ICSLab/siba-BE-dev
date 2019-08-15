package org.icslab.sibadev.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoTokenDetailDTO {

    private String access_token;

    private String refresh_token;

    private Long refresh_token_expires_in;

    private Long expires_in;

    private String scope;

    private String token_type;
}
