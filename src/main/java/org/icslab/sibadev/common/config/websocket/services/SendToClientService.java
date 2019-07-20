package org.icslab.sibadev.common.config.websocket.services;

import org.icslab.sibadev.common.config.websocket.domain.DeviceEstablishMessage;
import org.icslab.sibadev.common.config.websocket.domain.KeepAliveClientMessage;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendToClientService{

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendToReactClient(VirtualHubVO virtualHubVO, int messageType){
        System.out.println("send to client: "+virtualHubVO.getUserId());
        simpMessagingTemplate.convertAndSend("/topic/keep-alive-"+virtualHubVO.getUserId()
                , new KeepAliveClientMessage(
                        virtualHubVO.getHubName(),
                        messageType,
                        virtualHubVO.getHubId()
                ));
    }

    public void sendToReactClient(String mac, int messageType, Integer userId){
        simpMessagingTemplate.convertAndSend("/topic/device-conn-"+userId.toString()
                , new DeviceEstablishMessage(mac, messageType));
    }
}
