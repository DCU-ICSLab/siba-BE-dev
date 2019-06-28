package org.icslab.sibadev.devices.device;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxGraphDTO;
import org.icslab.sibadev.devices.device.services.TextBoxGraphDeployService;
import org.icslab.sibadev.devices.device.services.TextBoxGraphGenerateService;
import org.icslab.sibadev.devices.device.services.TextBoxGraphInsertionService;
import org.icslab.sibadev.devices.device.services.UniqueKeyGenService;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class DeviceController {

    @Autowired
    private TextBoxGraphGenerateService textBoxGraphGenerateService;

    @Autowired
    private TextBoxGraphInsertionService textBoxGraphInsertionService;

    @Autowired
    private TextBoxGraphDeployService textBoxGraphDeployService;

    @Autowired
    private UniqueKeyGenService uniqueKeyGenService;

    @Autowired
    private DeviceMapper deviceMapper;

   @GetMapping("/device/{authKey}")
    public ResponseDTO getDeviceInformation(@PathVariable String authKey){

        TextBoxGraphDTO textBoxGraphDTO = textBoxGraphGenerateService.generate(authKey);
        System.out.println(textBoxGraphDTO);
        return ResponseDTO.builder()
                .msg("textbox graph info")
                .status(HttpStatus.OK)
                .data(textBoxGraphDTO)
                .build();
    }

    @PostMapping("/device")
    public ResponseDTO createIoTDevice(@RequestBody DeviceDTO deviceDTO){

       deviceDTO.setBoxIdCnt(1);
       deviceDTO.setCodeCnt(0);
       deviceDTO.setEvCodeCnt(0);
       deviceDTO.setHaveEntry(false);

        System.out.println(deviceDTO);

       deviceMapper.createDevice(deviceDTO);

       return ResponseDTO.builder()
               .msg("IoT device create is success")
               .status(HttpStatus.OK)
               .data(deviceDTO)
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

    @PostMapping("/device/authkey")
    public ResponseDTO createAndReturnUniqueKey(){
        return ResponseDTO.builder()
                .msg("device authentication key")
                .status(HttpStatus.OK)
                .data(uniqueKeyGenService.generate())
                .build();
    }
}
