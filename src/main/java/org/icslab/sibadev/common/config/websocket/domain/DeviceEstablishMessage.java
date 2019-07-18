package org.icslab.sibadev.common.config.websocket.domain;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class DeviceEstablishMessage implements Serializable {

    private final String mac;

    private final int msgType;

    public DeviceEstablishMessage(String mac, int msgType){
        this.mac=mac;
        this.msgType=msgType;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getMac() {
        return mac;
    }
}
