package org.icslab.sibadev.devices.test.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TestSetDTO {

    private Integer vhubId;

    private Integer devId;

    private Integer userId;

    private Integer testId;

    private String devMac;

    private List<CommandDTO> cmdList;
}
