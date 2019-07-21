package org.icslab.sibadev.common.config.redis.listeners;

import org.icslab.sibadev.common.config.websocket.services.SendToClientService;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubVO;
import org.icslab.sibadev.mappers.CLogMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeepAliveKeyExpirationListener implements MessageListener {

    @Autowired
    private SendToClientService sendToClientService;

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private CLogMapper cLogMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //testId expire인 경우에는 filtering 해야
        System.out.println(pattern);

        VirtualHubVO virtualHubVO = virtualHubMapper.getHubOwner(message.toString());

        sendToClientService.sendToReactClient(virtualHubVO,0);
        virtualHubMapper.updateHubStatus(message.toString(), false); //허브 상태 갱신

        //허브에 연결된 레포지토리들의 장비들 제거
        List<Integer> repoList= virtualHubMapper.getAllLinkedRepoId(virtualHubVO.getHubId());
        for (Integer devId: repoList) {
            deviceMapper.deleteConnectedDeviceById(devId);
        }

        cLogMapper.insertCLog(virtualHubVO.getUserId(),"2");
        System.out.println("expire");
    }
}
