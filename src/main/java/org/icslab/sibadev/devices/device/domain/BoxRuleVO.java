package org.icslab.sibadev.devices.device.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoxRuleVO {

    //box info

    private Integer boxId;

    private Integer devId;

    private String preText;

    private String postText;

    private Integer headRow;

    private Integer footRow;

    //Rule info

    private Integer modId;

    private String dataKey;

    private String ruleType;

    private String ruleValue;

    private String mapVal;
}