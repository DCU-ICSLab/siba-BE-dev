package org.icslab.sibadev.devices.test.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@Builder
public class TestLogDTO {

    private Integer testId;

    private Integer devId;

    private String devMac;

    private String testStatus;

    private Timestamp finishedAt;

    private Long durationAt;
}
