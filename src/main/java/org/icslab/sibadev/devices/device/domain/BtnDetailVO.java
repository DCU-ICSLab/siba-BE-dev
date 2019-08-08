package org.icslab.sibadev.devices.device.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BtnDetailVO {

    private String btnName;

    private Integer btnCode;

    private Integer boxId;

    private Integer devId;

    private Integer idx;

    private Integer evCode;
}
