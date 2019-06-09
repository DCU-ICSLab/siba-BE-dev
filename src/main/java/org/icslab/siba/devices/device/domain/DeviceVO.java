package org.icslab.siba.devices.device.domain;

import lombok.Data;

@Data
public class DeviceVO {

    String authKey;

    Integer vHubId;

    String devName;

    String devDefName;

    String devType;

    String category;
}
