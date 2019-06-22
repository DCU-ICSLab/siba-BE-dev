package org.icslab.sibadev.devices.device.domain.deployset;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ButtonDTO {
    private String btnName;

    private Integer btnCode;

    private Integer idx;

    private Integer boxId;

    private Integer eventCode;

    private Boolean isSpread;
}
