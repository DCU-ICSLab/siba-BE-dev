package org.icslab.sibadev.common.config.rabbit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.io.Serializable;

@ToString
public final class KeepAliveMessage implements Serializable {

    private final String id;

    public KeepAliveMessage(@JsonProperty("id") String id){
        this.id=id;
    }

    public String getId(){
        return id;
    }
}
