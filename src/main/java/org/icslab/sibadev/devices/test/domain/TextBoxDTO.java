package org.icslab.sibadev.devices.test.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class TextBoxDTO {

    private Integer boxId;

    private String preText;

    private String postText;

    private Integer boxType;

    private List<ButtonVO> buttons;
}
