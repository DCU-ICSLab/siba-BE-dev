package org.icslab.siba.devices.device;

import lombok.extern.slf4j.Slf4j;
import org.icslab.siba.devices.device.domain.TextBoxGraphDTO;
import org.icslab.siba.devices.device.services.TextBoxGraphGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DeviceController {

    @Autowired
    TextBoxGraphGenerateService textBoxGraphGenerateService;

    @GetMapping("/device/{authKey}")
    public TextBoxGraphDTO getDeviceInformation(@PathVariable String authKey){
        return textBoxGraphGenerateService.generate(authKey);
    }
}
