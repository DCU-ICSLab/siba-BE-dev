package org.icslab.sibadev.devices.vhub.services;

import org.icslab.sibadev.common.config.redis.repository.KeepAliveRepository;
import org.icslab.sibadev.common.config.websocket.services.SendToClientService;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubVO;
import org.icslab.sibadev.mappers.CLogMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.icslab.sibadev.common.config.redis.RedisConstants.HUB_PREFIX;

@Service
public class HubEstablishService {

    @Autowired
    VirtualHubMapper virtualHubMapper;

    @Autowired
    KeepAliveRepository keepAliveRepository;

    @Autowired
    SendToClientService sendToClientService;

    @Autowired
    CLogMapper cLogMapper;

    public void establish(String hubAuthKey, String hubIp, Integer hubPort){
        VirtualHubVO virtualHubVO= virtualHubMapper.getHubOwner(hubAuthKey);

        //인증키가 DB에 존재하지 않는다면 수행하지 않는다.
        if(virtualHubVO!=null) {
            System.out.println("hub connection: "+hubAuthKey);
            keepAliveRepository.save(HUB_PREFIX+hubAuthKey, true);
            sendToClientService.sendToReactClient(virtualHubVO, 1);
            //허브 연결 상태로 변경
            virtualHubMapper.establishHub(hubAuthKey, hubIp, hubPort, true);
            cLogMapper.insertCLog(virtualHubVO.getUserId(), "1");
        }
    }
}
