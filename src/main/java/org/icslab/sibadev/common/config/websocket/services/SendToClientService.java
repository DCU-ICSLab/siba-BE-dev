package org.icslab.sibadev.common.config.websocket.services;

import org.icslab.sibadev.common.config.websocket.domain.DeviceControlResultMessage;
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

    //hub connection info send to client
    public void sendToReactClient(VirtualHubVO virtualHubVO, int messageType){
        System.out.println("send to client: "+virtualHubVO.getUserId());
        simpMessagingTemplate.convertAndSend("/topic/keep-alive-"+virtualHubVO.getUserId()
                , new KeepAliveClientMessage(
                        virtualHubVO.getHubName(),
                        messageType,
                        virtualHubVO.getHubId()
                ));
    }

    //device connection info send to client
    public void sendToReactClient(String mac, int messageType, Integer userId){
        simpMessagingTemplate.convertAndSend("/topic/device-conn-"+userId.toString()
                , new DeviceEstablishMessage(mac, messageType));
    }

    //test result send to client
    public void sendToReactClient(Integer userId, DeviceControlResultMessage deviceControlResultMessage){
        simpMessagingTemplate.convertAndSend("/topic/test-finish-"+userId.toString(), deviceControlResultMessage);
    }
}
