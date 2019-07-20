package org.icslab.sibadev.common.config.rabbit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class DeivceEstablishMessage implements Serializable {
    private final String mac;

    private final String devKey;

    private final int msgType;

    public DeivceEstablishMessage(
            @JsonProperty("mac") String mac,
            @JsonProperty("devKey") String devKey,
            @JsonProperty("msgType") int msgType
    )
    {
        this.mac=mac;
        this.devKey=devKey;
        this.msgType=msgType;
    }

    public String getMac(){
        return mac;
    }

    public String getDevKey(){
        return devKey;
    }

    public int getMsgType(){
        return msgType;
    }
}
