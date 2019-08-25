package org.icslab.sibadev.devices.device.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyBoxDTO {

    private Integer eventId;

    private String preText;

    private String postText;

    private Integer headRow;

    private Integer footRow;
}
