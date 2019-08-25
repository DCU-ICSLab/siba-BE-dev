package org.icslab.sibadev.devices.device.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventVO {
    private Integer eventId;

    private String dataKey;

    private String outputType;

    private String ruleType;

    private String ruleValue;

    private String preText;

    private String postText;

    private Integer headRow;

    private Integer footRow;

    private String host;

    private String port;

    private String path;

    private Integer evCode;
}
