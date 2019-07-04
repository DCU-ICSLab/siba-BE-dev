package org.icslab.sibadev.devices.device.domain.textboxgraph;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ButtonWrapperDTO {
    private int boxId;

    private ButtonDTO buttonDTO;
}
