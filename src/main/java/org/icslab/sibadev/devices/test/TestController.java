package org.icslab.sibadev.devices.test;

import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.test.domain.BoxIndexDTO;
import org.icslab.sibadev.devices.test.domain.TestResponseDTO;
import org.icslab.sibadev.devices.test.domain.TestSetDTO;
import org.icslab.sibadev.devices.test.domain.TextBoxDTO;
import org.icslab.sibadev.devices.test.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TestController {

    @Autowired
    private DeviceBoxSearchSerivce deviceBoxSearchSerivce;

    @Autowired
    private DeviceTestService deviceTestService;

    @Autowired
    private GetReservationService getReservationService;

    @Autowired
    private CancelReservationService cancelReservationService;

    @Autowired
    private GetDeviceStateService getDeviceStateService;

    @GetMapping("/test/{devId}/box/{boxId}")
    public ResponseDTO getBox(@PathVariable Integer devId, @PathVariable Integer boxId){

        //텍스트 박스 조회
        TextBoxDTO  textBoxDTO = deviceBoxSearchSerivce.search(devId, boxId);

        return ResponseDTO.builder()
                .status(HttpStatus.OK)
                .data(textBoxDTO)
                .build();
    }

    @GetMapping("/test/{vHubId}/reservation/{devMac}")
    public ResponseDTO getReservation(@PathVariable String devMac, @PathVariable Integer vHubId){

        TextBoxDTO textBoxDTO = getReservationService.getReservationForHub(devMac, vHubId);

        return ResponseDTO.builder()
                .data(textBoxDTO)
                .msg("reservation info")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/test/{devMac}")
    public ResponseDTO getBox(@PathVariable String devMac, @RequestBody TestSetDTO testSetDTO){
        System.out.println(devMac);
        System.out.println(testSetDTO);
        return deviceTestService.process(devMac, testSetDTO);
    }

    @PostMapping("/test/{vHubId}/reservation/{resId}")
    public ResponseDTO getReservation(@PathVariable Integer resId, @PathVariable Integer vHubId){

        TestResponseDTO testResponseDTO = cancelReservationService.cancelRequest(vHubId, resId);

        return ResponseDTO.builder()
                .msg(testResponseDTO.getMsg())
                .status(HttpStatus.valueOf(testResponseDTO.getStatus()))
                .build();
    }

    @PostMapping("/test/{vHubId}/state/{devMac}")
    public ResponseDTO getDeviceState(@PathVariable String devMac, @PathVariable Integer vHubId, @RequestBody BoxIndexDTO boxIndexDTO){

        try{
            TextBoxDTO textBoxDTO = getDeviceStateService.process(devMac, vHubId, boxIndexDTO.getDevId(), boxIndexDTO.getBoxId());
            return ResponseDTO.builder()
                    .data(textBoxDTO)
                    .msg("state info")
                    .status(HttpStatus.OK)
                    .build();
        }
        catch(Exception e){
            return ResponseDTO.builder()
                    .data(TextBoxDTO.builder()
                            .preText("요청하신 명령을 수행하는 중에 오류가 발생했습니다.")
                            .buttons(new ArrayList<>())
                            .boxType(6)
                            .boxId(-1)
                            .postText("")
                            .build())
                    .msg("state info")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}