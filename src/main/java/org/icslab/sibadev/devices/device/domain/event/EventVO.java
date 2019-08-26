package org.icslab.sibadev.devices.device.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventVO {
    private Integer eventId;

    private Integer priority;

    private String dataKey;

    private String outputType;

    private String ruleType;

    private String ruleValue;

    private String preText;

    private String postText;

    @JsonIgnore
    private Integer headRow;

    @JsonIgnore
    private Integer footRow;

    private String host;

    private String port;

    private String path;

    private Integer evCode;

    private String authKey;
}
