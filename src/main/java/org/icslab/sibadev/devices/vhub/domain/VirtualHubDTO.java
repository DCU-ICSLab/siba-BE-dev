package org.icslab.sibadev.devices.vhub.domain;

import lombok.Builder;
import lombok.Data;
import org.icslab.sibadev.devices.device.domain.DeviceShortDTO;

import java.util.List;

@Data
@Builder
public class VirtualHubDTO {

    private Integer vhubId;

    private String hubKey;

    private String hubName;

    private Boolean hubStatus;

    private String hubIp;

    private Integer hubPort;

    private char hubType;

    private List<DeviceShortDTO> devices;
}
