package org.icslab.sibadev.devices.device.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class SelectBoxDTO {

    private Integer boxId;

    private Integer devId;

    private String preText;

    private String postText;

    private Integer headRow;

    private Integer footRow;

    private List<StateRuleDTO> rules;
}
