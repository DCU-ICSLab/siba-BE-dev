package org.icslab.sibadev.devices.device;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.BtnDetailVO;
import org.icslab.sibadev.devices.device.domain.ConnectedDeviceVO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxGraphDTO;
import org.icslab.sibadev.devices.device.services.TextBoxGraphDeployService;
import org.icslab.sibadev.devices.device.services.TextBoxGraphGenerateService;
import org.icslab.sibadev.devices.device.services.TextBoxGraphInsertionService;
import org.icslab.sibadev.devices.device.services.UniqueKeyGenService;
import org.icslab.sibadev.mappers.DataModelMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private DataModelMapper dataModelMapper;

    @GetMapping("/device/{devId}")
    public ResponseDTO getDeviceInformation(@PathVariable Integer devId) {

        TextBoxGraphDTO textBoxGraphDTO = textBoxGraphGenerateService.generate(devId);
        System.out.println(textBoxGraphDTO);
        return ResponseDTO.builder()
                .msg("textbox graph info")
                .status(HttpStatus.OK)
                .data(textBoxGraphDTO)
                .build();
    }

    @PostMapping("/device")
    public ResponseDTO createIoTDevice(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody DeviceDTO deviceDTO) {

        Long userId = userPrincipal.getId();

        deviceDTO.setUserId(userId);
        deviceDTO.setBoxIdCnt(1);
        deviceDTO.setCodeCnt(0);
        deviceDTO.setEvCodeCnt(0);
        deviceDTO.setHaveEntry(false);

        System.out.println(deviceDTO);

        Map<String, Object> map =new HashMap<>();
        map.put("device", deviceDTO);
        deviceMapper.createDevice(map);
        deviceDTO.setDevId((Integer)map.get("devId"));

        return ResponseDTO.builder()
                .msg("IoT device create is success")
                .status(HttpStatus.OK)
                .data(deviceDTO)
                .build();
    }

    @PostMapping("/device/{devId}")
    public ResponseDTO saveDeviceInformation(@RequestBody TextBoxGraphDTO textBoxGraphDTO, @PathVariable Integer devId) {
        System.out.println(textBoxGraphDTO);

        textBoxGraphInsertionService.insertion(textBoxGraphDTO);

        return ResponseDTO.builder()
                .msg("textbox graph info")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/device/{devId}/deploy")
    public ResponseDTO saveDeviceInformation(@PathVariable Integer devId) {
        return textBoxGraphDeployService.deploy(devId);
    }

    @PostMapping("/device/authkey")
    public ResponseDTO createAndReturnUniqueKey() {
        return ResponseDTO.builder()
                .msg("device authentication key")
                .status(HttpStatus.OK)
                .data(uniqueKeyGenService.generate())
                .build();
    }

    @GetMapping("/device/{devId}/conndev")
    public ResponseDTO getConnectedDevInfo(@PathVariable Integer devId) {

        List<ConnectedDeviceVO> connectedDeviceVOS = deviceMapper.getConnectedDeviceInfo(devId);

        return ResponseDTO.builder()
                .msg("connected device list")
                .status(HttpStatus.OK)
                .data(connectedDeviceVOS)
                .build();
    }

    @GetMapping("/device/{devId}/model")
    public ResponseDTO getDataModelInfo(@PathVariable Integer devId){

        List<BtnDetailVO> selectSensing = deviceMapper.getBtnDetail(devId, "3"); //센싱-조회 버튼
        List<BtnDetailVO> deviceState = deviceMapper.getBtnDetail(devId, "4"); //디바이스-조회 버튼

        List<DataModelDTO> devState = dataModelMapper.getDataModel(devId, "0"); //디바이스 상태 모델
        List<DataModelDTO> sensingDt = dataModelMapper.getDataModel(devId, "1"); //센싱 데이터 모델

        return ResponseDTO.builder()
                .msg("device data model info")
                .status(HttpStatus.OK)
                .data(new Object(){
                    public List<BtnDetailVO> sensingBtn = selectSensing;
                    public List<BtnDetailVO> deviceStateBtn = deviceState;
                    public List<DataModelDTO> devStateModel = devState;
                    public List<DataModelDTO> sensingDataModel = sensingDt;
                })
                .build();
    }

    @PostMapping("/device/{devId}/model")
    public ResponseDTO addDataModel(@PathVariable Integer devId, @RequestBody DataModelDTO dataModelDTO) {

        dataModelMapper.insertDataModel(dataModelDTO);

        return ResponseDTO.builder()
                .msg("data model add success")
                .status(HttpStatus.OK)
                .data(dataModelDTO)
                .build();
    }
}
