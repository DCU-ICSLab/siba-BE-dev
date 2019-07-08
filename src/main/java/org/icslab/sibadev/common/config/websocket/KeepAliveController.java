package org.icslab.sibadev.common.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Profile("stomp")
@Component
public class KeepAliveController{

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendToReactClient(){
        //simpMessagingTemplate.convertAndSendToUser();
    }
}
