package org.icslab.sibadev.common.config.rabbit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@ToString
public class DeviceControlResultMessage implements Serializable {

    private final String devMac;

    private final int devId;

    private final String msg;

    private final HttpStatus status;

    private final Integer testId;

    private final int userId;

    public DeviceControlResultMessage(
            @JsonProperty("msg") String msg,
            @JsonProperty("status") int status,
            @JsonProperty("testId") Integer testId,
            @JsonProperty("userId") int userId,
            @JsonProperty("devId") int devId,
            @JsonProperty("devMac") String devMac
    )
    {
        this.msg=msg;
        this.status = HttpStatus.valueOf(status);
        this.testId=testId;
        this.userId=userId;
        this.devId=devId;
        this.devMac=devMac;
    }

    public String getMsg(){
        return msg;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public Integer getTestId() {
        return testId;
    }

    public int getUserId() {
        return userId;
    }

    public int getDevId() {
        return devId;
    }

    public String getDevMac() {
        return devMac;
    }
}
