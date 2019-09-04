package org.icslab.sibadev.common.config.websocket.domain;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class DeviceEstablishMessage implements Serializable {

    private final String mac;

    private final Integer devId;

    private final int msgType;

    public DeviceEstablishMessage(String mac, int msgType, Integer devId){
        this.mac=mac;
        this.msgType=msgType;
        this.devId=devId;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getMac() {
        return mac;
    }

    public Integer getDevId() {
        return devId;
    }
}
