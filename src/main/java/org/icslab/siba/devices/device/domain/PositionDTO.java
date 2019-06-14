package org.icslab.siba.devices.device.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PositionDTO {

    private double x;

    private double y;
}
