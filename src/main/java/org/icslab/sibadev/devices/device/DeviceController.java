package org.icslab.sibadev.devices.device;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.*;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelDTO;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelVO;
import org.icslab.sibadev.devices.device.domain.event.*;
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

import java.util.ArrayList;
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

        List<BoxRuleVO> boxRuleVOList = deviceMapper.getBoxAndRule(devId, "6");
        List<SelectBoxDTO> boxRuleHierarchy = new ArrayList<>();
        Integer boxId = -1;
        for (BoxRuleVO boxRule : boxRuleVOList){
            StateRuleDTO rule = null;
            if(boxRule.getModId()!=null){
                rule = StateRuleDTO.builder()
                        .boxId(boxRule.getBoxId())
                        .devId(boxRule.getDevId())
                        .dataKey(boxRule.getDataKey())
                        .modId(boxRule.getModId())
                        .ruleType(boxRule.getRuleType())
                        .ruleValue(boxRule.getRuleValue())
                        .mapVal(boxRule.getMapVal())
                        .modDevId(boxRule.getDevId())
                        .build();
            }
            if(boxId!=boxRule.getBoxId()){
                boxId=boxRule.getBoxId();
                List<StateRuleDTO> rules = new ArrayList<StateRuleDTO>();
                if(rule!=null)
                    rules.add(rule);
                boxRuleHierarchy.add(
                        SelectBoxDTO.builder()
                        .boxId(boxRule.getBoxId())
                        .devId(boxRule.getDevId())
                        .footRow(boxRule.getFootRow())
                        .headRow(boxRule.getHeadRow())
                        .postText(boxRule.getPostText())
                        .preText(boxRule.getPreText())
                        .rules(rules)
                        .build()
                );
            }
            else{
                if(rule!=null)
                    boxRuleHierarchy.get(boxRuleHierarchy.size()-1).getRules().add(rule);
            }
        }

        List<EventVO> eventVOS = dataModelMapper.getAllEvents(devId);
        List<EventDTO> eventDTOS = new ArrayList<>();

        for (EventVO event: eventVOS) {
            EventDTO eventDTO = EventDTO.builder()
                    .eventId(event.getEventId())
                    .dataKey(event.getDataKey())
                    .devId(devId)
                    .outputType(event.getOutputType())
                    .ruleType(event.getRuleType())
                    .ruleValue(event.getRuleValue())
                    .notifyBoxDTO(null)
                    .thirdServerDTO(null)
                    .controlDTO(null)
                    .build();

            switch (event.getOutputType()){

                //알림 톡
                case "1":
                    eventDTO.setNotifyBoxDTO(NotifyBoxDTO.builder()
                            .footRow(event.getFootRow())
                            .headRow(event.getHeadRow())
                            .preText(event.getPreText())
                            .postText(event.getPostText())
                            .build()
                    );
                    break;

                //제어
                case "2":
                    eventDTO.setControlDTO(ControlDTO.builder()
                            .evCode(event.getEvCode())
                            .build());
                    break;

                //3rd server
                default:
                    eventDTO.setThirdServerDTO(ThirdServerDTO.builder()
                            .host(event.getHost())
                            .port(event.getPort())
                            .path(event.getPath())
                            .build());
                    break;
            }
            eventDTOS.add(eventDTO);
        }


        List<DataModelDTO> devState = dataModelMapper.getDataModel(devId, "0"); //디바이스 상태 모델
        List<DataModelDTO> sensingDt = dataModelMapper.getDataModel(devId, "1"); //센싱 데이터 모델

        return ResponseDTO.builder()
                .msg("device data model info")
                .status(HttpStatus.OK)
                .data(new Object(){
                    public List<SelectBoxDTO> boxRules = boxRuleHierarchy;
                    public List<DataModelDTO> devStateModel = devState;
                    public List<DataModelDTO> sensingDataModel = sensingDt;
                    public List<EventDTO> events = eventDTOS;
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

    @PostMapping("/rule/{devId}")
    public ResponseDTO createStateRule(@PathVariable Integer devId, @RequestBody StateRuleDTO stateRuleDTO){

        Map<String,Object> map =new HashMap<>();
        map.put("rule", stateRuleDTO);

        System.out.println(stateRuleDTO);

        deviceMapper.createStateRule(map);
        stateRuleDTO.setModId((Integer) map.get("modId"));

        return ResponseDTO.builder()
                .data(stateRuleDTO)
                .status(HttpStatus.OK)
                .msg("new rule successfully add")
                .build();
    }

    @GetMapping("/model/{devType}")
    public ResponseDTO createStateRule(@PathVariable String devType){

        Integer devId = deviceMapper.findDevId(devType);

        return ResponseDTO.builder()
                .data(new Object(){
                    public List<DataModelVO> model = dataModelMapper.getDataModelWithKey(devId);
                    public List<EventVO> events = dataModelMapper.getAllEvents(devId);
                })
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/rule/{modId}/box/{boxId}/idx/{idx}")
    public ResponseDTO deleteRule(
            @PathVariable Integer modId,
            @PathVariable Integer boxId,
            @PathVariable Integer idx){

        Integer ReqboxId = boxId;
        Integer ReqIdx = idx;

        System.out.println(modId);

        dataModelMapper.deleteRuleWithModId(modId);

        return ResponseDTO.builder()
                .data(new Object(){
                    public Integer boxId = ReqboxId;
                    public Integer idx = ReqIdx;
                })
                .status(HttpStatus.OK)
                .msg("delete rule")
                .build();
    }

    @PostMapping("/event/{devId}")
    public ResponseDTO addEvent(@PathVariable Integer devId, @RequestBody EventDTO eventDTO){

        eventDTO.setDevId(devId);

        Map<String, Object> map =new HashMap<>();
        map.put("event", eventDTO);

        dataModelMapper.addEvent(map);
        eventDTO.setEventId((Integer)map.get("eventId"));

        switch (eventDTO.getOutputType()){

            //알림 톡
            case "1":
                NotifyBoxDTO notifyBoxDTO = eventDTO.getNotifyBoxDTO();
                notifyBoxDTO.setEventId(eventDTO.getEventId());
                dataModelMapper.addNotifyBox(notifyBoxDTO);
                break;

            //제어
            case "2":
                ControlDTO controlDTO = eventDTO.getControlDTO();
                controlDTO.setEventId(eventDTO.getEventId());
                dataModelMapper.addControl(controlDTO);
                break;

            //3rd server
            default:
                ThirdServerDTO thirdServerDTO =eventDTO.getThirdServerDTO();
                if(thirdServerDTO.getPort().equals(""))
                    thirdServerDTO.setPort("80");
                thirdServerDTO.setEventId(eventDTO.getEventId());
                dataModelMapper.addThirdServer(thirdServerDTO);
                break;
        }

        return ResponseDTO.builder()
                .data(eventDTO)
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/event/{eventId}/type/{type}")
    public ResponseDTO deleteEvent(@PathVariable Integer eventId, @PathVariable String type) {

        switch (type){

            //알림 톡
            case "1":
                dataModelMapper.deleteNotifyBox(eventId);
                break;

            //control
            case "2":
                dataModelMapper.deleteControl(eventId);
                break;

            //third server
            default:
                dataModelMapper.deleteThirdServer(eventId);
                break;
        }
        dataModelMapper.deleteEvent(eventId);

        return ResponseDTO.builder()
                .data(eventId)
                .status(HttpStatus.OK)
                .build();
    }
}
