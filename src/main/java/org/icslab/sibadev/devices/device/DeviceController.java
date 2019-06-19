package org.icslab.sibadev.devices.device;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxGraphDTO;
import org.icslab.sibadev.devices.device.services.TextBoxGraphDeployService;
import org.icslab.sibadev.devices.device.services.TextBoxGraphGenerateService;
import org.icslab.sibadev.devices.device.services.TextBoxGraphInsertionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class DeviceController {

    @Autowired
    TextBoxGraphGenerateService textBoxGraphGenerateService;

    @Autowired
    TextBoxGraphInsertionService textBoxGraphInsertionService;

    @Autowired
    TextBoxGraphDeployService textBoxGraphDeployService;

    @GetMapping("/device/{authKey}")
    public ResponseDTO getDeviceInformation(@PathVariable String authKey){
        return ResponseDTO.builder()
                .msg("textbox graph info")
                .status(HttpStatus.OK)
                .data(textBoxGraphGenerateService.generate(authKey))
                .build();
    }

    @PostMapping("/device/{authKey}")
    public ResponseDTO saveDeviceInformation(@RequestBody TextBoxGraphDTO textBoxGraphDTO, @PathVariable String authKey){
        System.out.println(textBoxGraphDTO);

        textBoxGraphInsertionService.insertion(textBoxGraphDTO);

        return ResponseDTO.builder()
                .msg("textbox graph info")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/device/{authKey}/deploy")
    public ResponseDTO saveDeviceInformation(@PathVariable String authKey){
        return textBoxGraphDeployService.deploy(authKey);
    }
}
