package org.icslab.sibadev.devices.device.domain.textboxgraph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkerDTO {
    private int parentId;

    private int code;

    private int childId;

    private PositionDTO m;

    private PositionDTO z;
}
