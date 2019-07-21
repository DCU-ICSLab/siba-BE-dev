package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.common.config.redis.repository.KeepAliveRepository;
import org.icslab.sibadev.common.config.redis.repository.TestKeyManagementRepository;
import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.test.constants.TestResponseMessageSet;
import org.icslab.sibadev.devices.test.domain.TestLogDTO;
import org.icslab.sibadev.devices.test.domain.TestResponseDTO;
import org.icslab.sibadev.devices.test.domain.TestSetDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubHostVO;
import org.icslab.sibadev.mappers.TestMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.Map;

@Service
public class DeviceTestService {

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private TestMapper testMapper;

    //@Autowired
    //private TestKeyManagementRepository testKeyManagementRepository;

    @Autowired
    private KeepAliveRepository keepAliveRepository;

    public ResponseDTO process(String devMac, TestSetDTO testSetDTO){

        ResponseDTO result =  ResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .msg(TestResponseMessageSet.HUB_TEST_FAIL_MESSAGE)
                .build();

        VirtualHubHostVO virtualHubHostVO = virtualHubMapper.getVirtualHubHostInfo(testSetDTO.getVhubId());
        String hubHost = virtualHubHostVO.getHost();
        Integer port = virtualHubHostVO.getPort();

        //허브가 현재 연결되어 있다면 수행
        if(keepAliveRepository.find(virtualHubHostVO.getHubAuthKey())!=null){
            RestTemplate restTemplate = new RestTemplate();

            TestLogDTO testLogDTO = TestLogDTO.builder()
                    .testStatus('2') //상태는 pending
                    .devId(testSetDTO.getDevId())
                    .devMac(devMac)
                    .build();

            Map<String, Object> map = new HashMap<>();
            map.put("testLogDTO", testLogDTO);
            testMapper.addDeviceTestLog(map); //pending test log 추가
            testSetDTO.setDevMac(devMac);
            Integer testId = Integer.valueOf(map.get("testId").toString()); //sequence 값 추출
            testSetDTO.setTestId(testId);

            System.out.println(testSetDTO);

            try{
                TestResponseDTO testResponseDTO = restTemplate.postForObject("http://"+hubHost+":"+port+"/dev/"+devMac, testSetDTO, TestResponseDTO.class);

                Long startedAt = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
                //testKeyManagementRepository.saveTestId(testId, startedAt); //test timeout 설정

                result = ResponseDTO.builder()
                        .status(HttpStatus.valueOf(testResponseDTO.getStatus()))
                        .msg(testResponseDTO.getMsg())
                        .data(testLogDTO)
                        .build();
                return result;
            }catch (Exception e){
                testMapper.changeTestLogStatus(testId, '1'); //실패로 변경
                return result;
            }
        }
        else{
            return result;
        }
    }
}
