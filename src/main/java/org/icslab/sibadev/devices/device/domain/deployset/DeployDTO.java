package org.icslab.sibadev.devices.device.domain.deployset;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.StateRuleDTO;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelDTO;
import org.icslab.sibadev.devices.device.domain.event.EventDTO;

import java.util.List;

@Data
@Builder
@ToString
public class DeployDTO {
    private DeviceDTO device;

    private List<BoxDTO> boxDTOList;

    private List<ButtonDTO> buttonDTOList;

    private List<DerivationDTO> derivationDTOList;

    private List<DataModelDTO> dataModelList;

    private List<StateRuleDTO> rules;

    private List<EventDTO> events;
}
