package org.icslab.sibadev.common.config.websocket.domain;

import lombok.ToString;

import java.io.Serializable;

@ToString
public final class KeepAliveClientMessage implements Serializable {

    private final String hubName;

    private final int msgType;

    public KeepAliveClientMessage(String hubName, int msgType){
        this.hubName=hubName;
        this.msgType=msgType;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getHubName() {
        return hubName;
    }
}
