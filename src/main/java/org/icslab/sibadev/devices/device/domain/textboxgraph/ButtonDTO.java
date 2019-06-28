package org.icslab.sibadev.devices.device.domain.textboxgraph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ButtonDTO {
    private int code;

    private String name;

    private LinkerDTO linker;

    private int idx;

    private Boolean isSpread;

    private Integer eventCode;

    private String type;
}
