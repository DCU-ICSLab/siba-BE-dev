package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.common.config.redis.repository.KeepAliveRepository;
import org.icslab.sibadev.common.config.redis.repository.TestKeyManagementRepository;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.test.constants.TestResponseMessageSet;
import org.icslab.sibadev.devices.test.domain.*;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubHostVO;
import org.icslab.sibadev.mappers.TestMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.icslab.sibadev.common.config.redis.RedisConstants.*;

@Service
public class DeviceTestService {

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestKeyManagementRepository testKeyManagementRepository;

    @Autowired
    private KeepAliveRepository keepAliveRepository;

    private ResponseDTO sendToHub(TestSetDTO testSetDTO, String devMac, VirtualHubHostVO virtualHubHostVO){

        RestTemplate restTemplate = new RestTemplate();
        String hubHost = virtualHubHostVO.getHost();
        Integer port = virtualHubHostVO.getPort();

        TestLogDTO testLogDTO = TestLogDTO.builder()
                .testStatus("2") //상태는 pending
                .devId(testSetDTO.getDevId())
                .devMac(devMac)
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("testLogDTO", testLogDTO);

        testMapper.addDeviceTestLog(map); //pending test log 추가

        Integer testId = Integer.valueOf(map.get("testId").toString()); //sequence 값 추출

        testSetDTO.setDevMac(devMac);
        testSetDTO.setTestId(testId);
        testLogDTO.setTestId(testId);

        Long startedAt = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        testKeyManagementRepository.saveTestId(TEST_PREFIX + testId.toString(), startedAt); //test timeout 설정

        try {

            //허브에게 명령 전송
            TestResponseDTO testResponseDTO = restTemplate.postForObject("http://" + hubHost + ":" + port + "/dev/" + devMac, testSetDTO, TestResponseDTO.class);

            return ResponseDTO.builder()
                    .status(HttpStatus.valueOf(testResponseDTO.getStatus()))
                    .msg(testResponseDTO.getMsg())
                    .data(testLogDTO)
                    .build();
        } catch (Exception e) {

            //명령이 허브까지 도달하지 못하였다면
            testMapper.changeTestLogStatus(testId, "1"); //실패로 변경
            System.out.println("Exception is occured");
            return ResponseDTO.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .msg(TestResponseMessageSet.HUB_TEST_FAIL_MESSAGE)
                    .data(testLogDTO)
                    .build();
        }
    }

    private List<Integer> getReservationCount(TestSetDTO testSetDTO, String devMac){
        int index = 0;
        List<Integer> list =new ArrayList<>();
        for (CommandDTO commandDTO : testSetDTO.getCmdList()) {
            if (commandDTO.getBtnType().equals("5")) {
                list.add(index++);
            }
        }
        return list;
    }

    public ResponseDTO process(String devMac, TestSetDTO testSetDTO){

        VirtualHubHostVO virtualHubHostVO = virtualHubMapper.getVirtualHubHostInfo(testSetDTO.getVhubId());

        //허브가 현재 연결되어 있다면 수행
        if(keepAliveRepository.find(HUB_PREFIX+virtualHubHostVO.getHubAuthKey())!=null) {

            //명령이 모두다 예약 명령이 아니라면 전송
            List<Integer> cmdList =this.getReservationCount(testSetDTO, devMac);
            //if (cmdList.size() != testSetDTO.getCmdList().size()) {
                return sendToHub(testSetDTO, devMac, virtualHubHostVO);
            //}

            //명령이 모두다 예약 명령이라면
            /*else{
                for(Integer index : cmdList){
                    CommandDTO commandDTO = testSetDTO.getCmdList().get(index);

                    Long reservationAt = null;

                    for (OptionalDataDTO optionalDataDTO : commandDTO.getAdditional()) {
                        if (optionalDataDTO.getType().equals("1")) {
                            reservationAt = (Long) optionalDataDTO.getValue();
                        }
                    }

                    Map<String, Object> m = new HashMap<>();
                    m.put("testLogDTO", TestLogDTO.builder()
                            .testStatus("2") //상태는 pending
                            .devId(testSetDTO.getDevId())
                            .devMac(devMac)
                            .build());
                    testMapper.addDeviceTestLog(m); //pending test log 추가

                    Integer reservationId = Integer.valueOf(m.get("testId").toString());

                    //test timeout 설정
                    Long startedAt = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
                    testKeyManagementRepository.saveTestId(TEST_RESERVATION_PREFIX + reservationId.toString(), startedAt, reservationAt);
                }
                return sendToHub(testSetDTO, devMac, virtualHubHostVO);
            }*/
        }
        else{
            return ResponseDTO.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .msg(TestResponseMessageSet.HUB_TEST_FAIL_MESSAGE)
                    .build();
        }
    }
}
