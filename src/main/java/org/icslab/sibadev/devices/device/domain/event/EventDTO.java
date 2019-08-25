package org.icslab.sibadev.devices.device.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Integer eventId;

    private Integer devId;

    private String dataKey;

    private String outputType;

    private String ruleType;

    private String ruleValue;

    private ControlDTO controlDTO;

    private NotifyBoxDTO notifyBoxDTO;

    private  ThirdServerDTO thirdServerDTO;
}
