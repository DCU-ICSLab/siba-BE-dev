package org.icslab.sibadev.devices.device.domain.textboxgraph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentBoxDTO {
    private Integer parentId;
    private Integer code;
}
