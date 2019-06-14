package org.icslab.siba.devices.device.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkerDTO {
    private int parentId;

    private int code;

    private int childId;

    private PositionDTO m;

    private PositionDTO z;
}
