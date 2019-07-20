package org.icslab.sibadev.devices.test.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OptionalDataDTO {

    private String type;

    private Object value;
}
