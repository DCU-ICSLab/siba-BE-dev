package org.icslab.sibadev.devices.device.domain.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataModelVO {

    private String dataKey;

    private String dataType;

    private Boolean isEv;

    private String modType;
}
