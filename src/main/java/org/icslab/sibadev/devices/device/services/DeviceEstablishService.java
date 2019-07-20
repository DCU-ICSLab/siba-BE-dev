package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.common.config.websocket.services.SendToClientService;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceEstablishService {

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    SendToClientService sendToClientService;

    public void establish(String devAuthKey, String mac, int msgType){

        Integer userId = deviceMapper.getRepositoryOwner(devAuthKey);

        //인증키가 DB에 존재하지 않는다면 수행하지 않는다.
        if(userId!=null) {
            sendToClientService.sendToReactClient(mac, msgType, userId);
        }
    }
}
