package org.icslab.sibadev.devices.test.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ButtonVO {
    private Integer btnCode;

    private String btnName;

    private Integer evCode;

    private String btnType;

    private Integer cboxId;
}
