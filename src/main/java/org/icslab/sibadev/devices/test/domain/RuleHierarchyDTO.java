package org.icslab.sibadev.devices.test.domain;

import lombok.Builder;
import lombok.Data;
import org.icslab.sibadev.devices.device.domain.StateRuleDTO;

import java.util.List;

@Data
@Builder
public class RuleHierarchyDTO {

    private String dataKey;

    private List<StateRuleDTO> rules;

}
