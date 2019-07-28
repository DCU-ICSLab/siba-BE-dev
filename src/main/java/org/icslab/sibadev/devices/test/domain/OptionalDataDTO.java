package org.icslab.sibadev.devices.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OptionalDataDTO {

    private String type;

    private Object value;
}
