package org.icslab.sibadev.devices.test;

import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.test.domain.TestSetDTO;
import org.icslab.sibadev.devices.test.domain.TextBoxDTO;
import org.icslab.sibadev.devices.test.services.DeviceBoxSearchSerivce;
import org.icslab.sibadev.devices.test.services.DeviceTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private DeviceBoxSearchSerivce deviceBoxSearchSerivce;

    @Autowired
    private DeviceTestService deviceTestService;

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
    public ResponseDTO getBox(@PathVariable String devMac, @RequestBody TestSetDTO testSetDTO){
        System.out.println(devMac);
        System.out.println(testSetDTO);
        return deviceTestService.process(devMac, testSetDTO);
    }
}
