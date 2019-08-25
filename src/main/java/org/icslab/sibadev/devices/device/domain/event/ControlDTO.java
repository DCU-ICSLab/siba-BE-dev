package org.icslab.sibadev.devices.device.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ControlDTO {

    private Integer eventId;

    private Integer evCode;
}
