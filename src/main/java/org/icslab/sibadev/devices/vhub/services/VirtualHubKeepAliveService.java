package org.icslab.sibadev.devices.vhub.services;

import org.icslab.sibadev.common.config.redis.repository.KeepAliveRepository;
import org.icslab.sibadev.common.config.websocket.services.SendToClientService;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirtualHubKeepAliveService {

    @Autowired
    private KeepAliveRepository keepAliveRepository;

    @Autowired
    private SendToClientService sendToClientService;

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    public void keep(String hubAuthKey){

        //해당 hubAuthKey가 in-memory에 존재 하는 경우 유효 시간 갱신 작업 수행
        if(keepAliveRepository.find(hubAuthKey)!=null){
            keepAliveRepository.update(hubAuthKey, true);
        }

        //해당 hubAuthKey가 in-memory에 존재 하지 않는 경우, 허브 연결
        else{
            keepAliveRepository.save(hubAuthKey, true);
            sendToClientService.sendToReactClient(virtualHubMapper.getHubOwner(hubAuthKey),1);
        }
    }
}
