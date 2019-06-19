package org.icslab.sibadev.devices.device.domain.deployset;

import lombok.Data;

@Data
public class ButtonDTO {
    private String btnName;

    private String btnCode;

    private Integer idx;

    private Integer boxId;

    private Integer evCode;

    private Boolean isSpread;
}
