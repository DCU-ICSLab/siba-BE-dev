package org.icslab.sibadev.common.config.rabbit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.io.Serializable;

@ToString
public final class EstablishMessage implements Serializable {

    private final String id;

    private final String ip;

    private final Integer port;

    public EstablishMessage(
            @JsonProperty("id") String id,
            @JsonProperty("ip") String ip,
            @JsonProperty("port") Integer port
    )
    {
        this.id=id;
        this.ip=ip;
        this.port=port;
    }

    public String getId(){
        return id;
    }

    public String getIp(){
        return ip;
    }

    public Integer getPort(){
        return port;
    }
}
