package org.icslab.sibadev.common.config.rabbit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class DeivceEstablishMessage implements Serializable {
    private final String mac;

    private final String devKey;

    public DeivceEstablishMessage(
            @JsonProperty("mac") String mac,
            @JsonProperty("devKey") String devKey
    )
    {
        this.mac=mac;
        this.devKey=devKey;
    }

    public String getMac(){
        return mac;
    }

    public String getDevKey(){
        return devKey;
    }
}
