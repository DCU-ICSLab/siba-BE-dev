package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.common.config.rabbit.domain.DeviceControlResultMessage;
import org.icslab.sibadev.common.config.redis.repository.TestKeyManagementRepository;
import org.icslab.sibadev.common.config.websocket.services.SendToClientService;
import org.icslab.sibadev.devices.test.domain.TestLogDTO;
import org.icslab.sibadev.mappers.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

@Service
public class DeviceControlResultProcessService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private SendToClientService sendToClientService;

    //@Autowired
    //private TestKeyManagementRepository testKeyManagementRepository;

    //디바이스로부터 명령 결과 응답 처리
    public void process(DeviceControlResultMessage message){

        //테스트가 timeout을 초과하지 않았다면 수행
        //Long statedAt = testKeyManagementRepository.findTestId(message.getTestId());
        //if(statedAt!=null){

            //startedAt
            //LocalDateTime startedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(statedAt), TimeZone.getDefault().toZoneId());
            LocalDateTime startedAt = LocalDateTime.now();

            //finished time
            LocalDateTime current = LocalDateTime.now();

            Long durationAt = current.until(startedAt, ChronoUnit.SECONDS);
            Timestamp finishedAt = Timestamp.valueOf(current);

            Character testStatus = message.getStatus() == HttpStatus.OK ? '1' : '2';

            //테스트 상태 값 업데이트

        //오류 발생  Execution of Rabbit message listener failed.
            testMapper.changeTestLog(
                    TestLogDTO.builder()
                            .testId(message.getTestId())
                            .durtaionAt(finishedAt)
                            .finishedAt(finishedAt)
                            .testStatus(testStatus) //성공
                            .build()
            );

            //클라이언트에게 전송
            sendToClientService.sendToReactClient(message.getUserId(),
                    new org.icslab.sibadev.common.config.websocket.domain.DeviceControlResultMessage(
                            message.getTestId(),
                            message.getMsg(),
                            testStatus,
                            finishedAt,
                            durationAt
                    )
            );
        //}
    }
}
