package org.icslab.sibadev.devices.test;

import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.test.constants.TestResponseMessageSet;
import org.icslab.sibadev.devices.test.domain.TestLogDTO;
import org.icslab.sibadev.devices.test.domain.TestResponseDTO;
import org.icslab.sibadev.devices.test.domain.TestSetDTO;
import org.icslab.sibadev.devices.test.domain.TextBoxDTO;
import org.icslab.sibadev.devices.test.services.DeviceBoxSearchSerivce;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubHostVO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.TestMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.icslab.sibadev.user.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    private DeviceBoxSearchSerivce deviceBoxSearchSerivce;

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @GetMapping("/test/{devId}/box/{boxId}")
    public ResponseDTO getBox(@PathVariable Integer devId, @PathVariable Integer boxId){

        //텍스트 박스 조회
        TextBoxDTO  textBoxDTO = deviceBoxSearchSerivce.search(devId, boxId);

        return ResponseDTO.builder()
                .status(HttpStatus.OK)
                .data(textBoxDTO)
                .build();
    }

    @PostMapping("/test/{devMac}")
    public ResponseDTO getBox(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable String devMac, @RequestBody TestSetDTO testSetDTO){

        System.out.println(testSetDTO);

        VirtualHubHostVO virtualHubHostVO = virtualHubMapper.getVirtualHubHostInfo(testSetDTO.getVhubId());
        String hubHost = virtualHubHostVO.getHost();
        Integer port = virtualHubHostVO.getPort();

        testSetDTO.setUserId(userPrincipal.getId().toString());

        if(hubHost!=null && port !=null){
            RestTemplate restTemplate = new RestTemplate();
            System.out.println("send to Hub");

            TestLogDTO testLogDTO = TestLogDTO.builder()
                    .testStatus('2') //상태는 pending
                    .devId(testSetDTO.getDevId())
                    .devMac(devMac)
                    .build();

            testMapper.addDeviceTestLog(testLogDTO);

            Integer testId = testLogDTO.getTestId();
            System.out.println(testId);

            try{
                TestResponseDTO testResponseDTO = restTemplate.postForObject("http://"+hubHost+":"+port+"/dev/"+devMac, testSetDTO, TestResponseDTO.class);

                return ResponseDTO.builder()
                        .status(HttpStatus.valueOf(testResponseDTO.getStatus()))
                        .msg(testResponseDTO.getMsg())
                        .build();
            }catch (Exception e){
                testMapper.changeTestLogStatus(testId, '1'); //실패로 변경
                return ResponseDTO.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .msg(TestResponseMessageSet.HUB_TEST_FAIL_MESSAGE)
                        .build();
            }
        }
        else{
            return ResponseDTO.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .msg(TestResponseMessageSet.HUB_TEST_FAIL_MESSAGE)
                    .build();
        }
    }
}
