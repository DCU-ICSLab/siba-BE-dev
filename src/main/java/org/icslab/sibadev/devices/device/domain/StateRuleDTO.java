package org.icslab.sibadev.devices.device.domain;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateRuleDTO {

    private Integer modId;

    private Integer devId;

    private Integer modDevId;

    private Integer boxId;

    private String dataKey;

    private String ruleType;

    private String ruleValue;

    private String mapVal;

    private Integer priority;
}
