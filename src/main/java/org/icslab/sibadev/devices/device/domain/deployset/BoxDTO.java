package org.icslab.sibadev.devices.device.domain.deployset;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoxDTO {
    private Integer boxId;

    private String preText;

    private String postText;

    private Integer boxType;
}
