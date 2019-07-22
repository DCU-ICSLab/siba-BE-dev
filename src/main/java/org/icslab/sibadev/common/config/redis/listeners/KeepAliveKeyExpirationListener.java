package org.icslab.sibadev.common.config.redis.listeners;

import org.icslab.sibadev.common.config.redis.RedisConstants;
import org.icslab.sibadev.common.config.redis.repository.TestKeyManagementRepository;
import org.icslab.sibadev.common.config.websocket.services.SendToClientService;
import org.icslab.sibadev.devices.test.domain.TestLogDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubVO;
import org.icslab.sibadev.mappers.CLogMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.TestMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.icslab.sibadev.common.config.redis.RedisConstants.HUB_PREFIX;
import static org.icslab.sibadev.common.config.redis.RedisConstants.TEST_PREFIX;
import static org.icslab.sibadev.devices.test.constants.TestResponseMessageSet.HUB_TEST_TIMEOUT_MESSAGE;

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

    @Autowired
    private TestMapper testMapper;

    @Autowired
    TestKeyManagementRepository testKeyManagementRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        //key값 필터링
        String key = message.toString();
        String[] keypair = key.split("_");
        String prefix = keypair[0]+"_";

        //허브 키 라면
        if(prefix.equals(HUB_PREFIX)){
            VirtualHubVO virtualHubVO = virtualHubMapper.getHubOwner(keypair[1]);

            sendToClientService.sendToReactClient(virtualHubVO,0);
            virtualHubMapper.updateHubStatus(keypair[1], false); //허브 상태 갱신

            //허브에 연결된 레포지토리들의 장비들 제거
            List<Integer> repoList= virtualHubMapper.getAllLinkedRepoId(virtualHubVO.getHubId());
            for (Integer devId: repoList) {
                deviceMapper.deleteConnectedDeviceById(devId);
            }

            cLogMapper.insertCLog(virtualHubVO.getUserId(),"2");
            System.out.println("hub expirte");
        }

        //test가 pending이였다면 수행
        else if(prefix.equals(TEST_PREFIX)){
            System.out.println("test expirte");
            Integer testId = Integer.parseInt(keypair[1]);

            if(!testMapper.getTestLogStatus(testId).equals(("2"))) return;

            System.out.println("sh");

            Timestamp finishedAt =  Timestamp.valueOf(LocalDateTime.now());
            Long duration = RedisConstants.TEST_WAIT_TIMEOUT;
            String testStatus = "1"; //실패

            testMapper.changeTestLog(
                    TestLogDTO.builder()
                            .testId(testId)
                            .durationAt(duration)
                            .finishedAt(finishedAt)
                            .testStatus(testStatus) //성공
                            .build()
            );

            sendToClientService.sendToReactClient(testMapper.getTestOwner(testId),
                    new org.icslab.sibadev.common.config.websocket.domain.DeviceControlResultMessage(
                            testId,
                            HUB_TEST_TIMEOUT_MESSAGE,
                            testStatus, //실패
                            finishedAt,
                            duration
                    )
            );
        }
    }
}
