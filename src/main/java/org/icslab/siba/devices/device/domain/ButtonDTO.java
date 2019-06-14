package org.icslab.siba.devices.device.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ButtonDTO {
    private int code;

    private String name;

    private LinkerDTO linker;
}
