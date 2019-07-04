package org.icslab.sibadev.devices.device.domain.textboxgraph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionDTO {

    private double x;

    private double y;
}
