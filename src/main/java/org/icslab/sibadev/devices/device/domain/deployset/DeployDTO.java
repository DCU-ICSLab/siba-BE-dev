package org.icslab.sibadev.devices.device.domain.deployset;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;

import java.util.List;

@Data
@Builder
@ToString
public class DeployDTO {
    private DeviceDTO device;

    private List<BoxDTO> boxDTOList;

    private List<ButtonDTO> buttonDTOList;

    private List<DerivationDTO> derivationDTOList;
}
