package org.icslab.siba.devices.device.domain;

import lombok.Data;

@Data
public class DeviceVO {

    private String authKey;

    private Integer vHubId;

    private String devName;

    private String devDefName;

    private String devType;

    private String category;

    private Integer boxIdCnt;

    private Integer codeCnt;

    private Boolean haveEntry;
}
