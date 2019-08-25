package org.icslab.sibadev.devices.device.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThirdServerDTO {

    private Integer eventId;

    private String host;

    private String port;

    private String path;
}
