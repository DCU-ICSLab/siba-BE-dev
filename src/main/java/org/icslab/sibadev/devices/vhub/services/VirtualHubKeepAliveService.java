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
public class VirtualHubKeepAliveService {

    @Autowired
    private KeepAliveRepository keepAliveRepository;

    @Autowired
    private SendToClientService sendToClientService;

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private CLogMapper cLogMapper;

    public void keep(String hubAuthKey){

        //hubAuthKey가 Null이라면 수행중지
        if(hubAuthKey==null){
            return;
        }

        //해당 hubAuthKey가 in-memory에 존재 하는 경우 유효 시간 갱신 작업 수행
        if(keepAliveRepository.find(HUB_PREFIX+hubAuthKey)!=null){
            //System.out.println("[update]: "+hubAuthKey);
            keepAliveRepository.update(HUB_PREFIX+hubAuthKey, true);
        }

        //해당 hubAuthKey가 in-memory에 존재 하지 않는 경우, 허브 연결
        /*else{
            VirtualHubVO virtualHubVO= virtualHubMapper.getHubOwner(hubAuthKey);

            //인증키가 DB에 존재하지 않는다면 수행하지 않는다.
            if(virtualHubVO!=null) {
                keepAliveRepository.save(hubAuthKey, true);
                sendToClientService.sendToReactClient(virtualHubVO, 1);
                virtualHubMapper.updateHubStatus(hubAuthKey, true); //허브 상태 갱신
                cLogMapper.insertCLog(virtualHubVO.getUserId(), "1");
            }
        }*/
    }
}
