package org.icslab.sibadev.common.config.websocket.domain;

import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@ToString
public class DeviceControlResultMessage implements Serializable {

    private final int testId;

    private final String msg;

    private final char status;

    private final Timestamp findishedAt;

    private final long duration;

    public DeviceControlResultMessage(int testId, String msg, char status, Timestamp findishedAt, long duration){
        this.testId=testId;
        this.msg=msg;
        this.status=status;
        this.findishedAt=findishedAt;
        this.duration=duration;
    }

    public int getTestId() {
        return testId;
    }

    public String getMsg() {
        return msg;
    }

    public char getStatus() {
        return status;
    }

    public long getDuration() {
        return duration;
    }

    public Timestamp getFindishedAt() {
        return findishedAt;
    }
}
