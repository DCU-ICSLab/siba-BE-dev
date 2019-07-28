package org.icslab.sibadev.devices.test;

import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.test.domain.TestResponseDTO;
import org.icslab.sibadev.devices.test.domain.TestSetDTO;
import org.icslab.sibadev.devices.test.domain.TextBoxDTO;
import org.icslab.sibadev.devices.test.services.CancelReservationService;
import org.icslab.sibadev.devices.test.services.DeviceBoxSearchSerivce;
import org.icslab.sibadev.devices.test.services.DeviceTestService;
import org.icslab.sibadev.devices.test.services.GetReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
