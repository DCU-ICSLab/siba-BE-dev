package org.icslab.sibadev.common.config.rabbit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.io.Serializable;

@ToString
public final class KeepAliveMessage implements Serializable {

    private final int id;

    public KeepAliveMessage(@JsonProperty("id") int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    /*@Override
    public String toString(){
        return "KeepAliveMessage{id="+id+"}";
    }*/
}
