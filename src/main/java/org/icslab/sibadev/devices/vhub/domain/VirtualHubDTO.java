package org.icslab.sibadev.devices.vhub.domain;

import lombok.Builder;
import lombok.Data;
import org.icslab.sibadev.devices.device.domain.DeviceShortDTO;

import java.util.List;

@Data
@Builder
public class VirtualHubDTO {

    public Integer vhubId;
    public List<DeviceShortDTO> devices;
}
