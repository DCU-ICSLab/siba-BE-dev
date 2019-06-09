package org.icslab.siba.devices.vhub.domain;

import lombok.Builder;
import lombok.Data;
import org.icslab.siba.devices.device.domain.DeviceVO;

import java.util.List;

@Data
@Builder
public class VirtualHubDTO {

    public Integer vhubId;
    public List<DeviceVO> devices;
}
