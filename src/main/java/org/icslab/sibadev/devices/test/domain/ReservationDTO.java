package org.icslab.sibadev.devices.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReservationDTO {

    @JsonProperty("res_id")
    private Integer resId;

    @JsonProperty("ev_code")
    private Integer eventCode;

    @JsonProperty("act_at")
    private Long actAt;
}
