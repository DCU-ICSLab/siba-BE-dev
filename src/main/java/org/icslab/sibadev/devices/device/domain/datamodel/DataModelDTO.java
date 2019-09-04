package org.icslab.sibadev.devices.device.domain.datamodel;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataModelDTO {

    private Integer modId;

    private String dataKey;

    private Integer devId;

    private String dataType;

    private String modType;

    private Boolean isEv;
}
