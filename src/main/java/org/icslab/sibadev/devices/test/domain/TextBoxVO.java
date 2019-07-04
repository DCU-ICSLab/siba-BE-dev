package org.icslab.sibadev.devices.test.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TextBoxVO {

    private Integer boxId;

    private String preText;

    private String postText;

    private Integer boxType;
}
