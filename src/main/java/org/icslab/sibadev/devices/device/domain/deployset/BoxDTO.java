package org.icslab.sibadev.devices.device.domain.deployset;

import lombok.Data;

@Data
public class BoxDTO {
    private Integer boxId;

    private String preText;

    private String postText;

    private Integer boxType;
}
