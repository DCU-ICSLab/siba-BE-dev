package org.icslab.sibadev.devices.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ButtonVO {
    private Integer btnCode;

    private String btnName;

    private Integer evCode;

    private String btnType;

    private Integer cboxId;
}
