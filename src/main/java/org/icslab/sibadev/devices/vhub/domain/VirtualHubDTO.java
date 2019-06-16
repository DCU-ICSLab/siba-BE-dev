package org.icslab.sibadev.devices.vhub.domain;

import lombok.Builder;
import lombok.Data;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;

import java.util.List;

@Data
@Builder
public class VirtualHubDTO {

    public Integer vhubId;
    public List<DeviceDTO> devices;
}
