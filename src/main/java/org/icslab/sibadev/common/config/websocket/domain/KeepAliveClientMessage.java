package org.icslab.sibadev.common.config.websocket.domain;

import lombok.ToString;

import java.io.Serializable;

@ToString
public final class KeepAliveClientMessage implements Serializable {

    private final int hubId;

    private final String hubName;

    private final int msgType;

    public KeepAliveClientMessage(String hubName, int msgType, int hubId){
        this.hubId=hubId;
        this.hubName=hubName;
        this.msgType=msgType;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getHubName() {
        return hubName;
    }

    public int getHubId() {
        return hubId;
    }
}
