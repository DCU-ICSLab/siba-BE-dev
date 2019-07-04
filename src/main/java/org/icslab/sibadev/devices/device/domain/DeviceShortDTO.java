package org.icslab.sibadev.devices.device.domain;

import lombok.Data;

@Data
public class DeviceShortDTO {
    private String authKey;

    private Integer vHubId;

    private Integer devId;

    private String devName;

    private String devDefName;

    private String devType;

    private String category;
}
