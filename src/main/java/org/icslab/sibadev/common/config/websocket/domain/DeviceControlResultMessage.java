package org.icslab.sibadev.common.config.websocket.domain;

import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@ToString
public class DeviceControlResultMessage implements Serializable {

    private final Integer testId;

    private final String msg;

    private final String status;

    private final Timestamp finishedAt;

    private final Long duration;

    public DeviceControlResultMessage(Integer testId, String msg, String status, Timestamp finishedAt, Long duration){
        this.testId=testId;
        this.msg=msg;
        this.status=status;
        this.finishedAt=finishedAt;
        this.duration=duration;
    }

    public Integer getTestId() {
        return testId;
    }

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public Long getDuration() {
        return duration;
    }

    public Timestamp getFinishedAt() {
        return finishedAt;
    }
}
