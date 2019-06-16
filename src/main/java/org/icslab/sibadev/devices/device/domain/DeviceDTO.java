package org.icslab.sibadev.devices.device.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceDTO {

    private String authKey;

    private Integer vHubId;

    private String devName;

    private String devDefName;

    private String devType;

    private String category;

    private Integer boxIdCnt;

    private Integer codeCnt;

    private Integer evCodeCnt;

    private Boolean haveEntry;
}
