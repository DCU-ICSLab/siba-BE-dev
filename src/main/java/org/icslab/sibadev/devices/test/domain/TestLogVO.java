package org.icslab.sibadev.devices.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class TestLogVO {
    private Integer testId;

    private String testStatus;

    private String devMac;

    private Timestamp finishedAt;

    private Timestamp durationAt;
}
