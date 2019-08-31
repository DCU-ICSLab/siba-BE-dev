package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.common.constants.SkillServer;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.deployset.DeployDTO;
import org.icslab.sibadev.devices.device.domain.event.*;
import org.icslab.sibadev.mappers.DataModelMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextBoxGraphDeployService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DataModelMapper dataModelMapper;

    public ResponseDTO deploy(Integer devId){

        List<EventVO> events = dataModelMapper.getAllEvents(devId);

        deviceMapper.updateDeployDate(devId);

        return this.sendToSkillServer(
                SkillServer.SKILL_SERVER_HOST+SkillServer.TEXTBOX_GRAPH_DEPLOY_URL,
                DeployDTO.builder()
                        .device(deviceMapper.getDevice(devId))
                        .boxDTOList(deviceMapper.getBoxes(devId))
                        .buttonDTOList(deviceMapper.getButtons(devId))
                        .derivationDTOList(deviceMapper.getDerivations(devId))
                        .dataModelList(dataModelMapper.getAllDataModel(devId))
                        .rules(dataModelMapper.getAllDeviceRules(devId))
                        .events(eventGrouping(events, devId))
                        .build()
        );
    }

    private ResponseDTO sendToSkillServer(String url, DeployDTO dataset){
        System.out.println(dataset);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, dataset, ResponseDTO.class);
    }

    private List<EventDTO> eventGrouping(List<EventVO> events, Integer devId){
        List<EventDTO> eventHierarchy = new ArrayList<>();

        for (EventVO event: events) {

            ControlDTO controlDTO = null;
            ThirdServerDTO thirdServerDTO = null;
            NotifyBoxDTO notifyBoxDTO = null;

            switch (event.getOutputType()){
                //알림톡
                case "1":
                    notifyBoxDTO = NotifyBoxDTO.builder()
                            .preText(event.getPreText())
                            .postText(event.getPostText())
                            .eventId(event.getEventId())
                            .build();
                    break;
                //제어
                case "2":
                    controlDTO = ControlDTO.builder()
                            .evCode(event.getEvCode())
                            .authKey(event.getAuthKey())
                            .eventId(event.getEventId())
                            .build();
                    break;
                //third
                default:
                    thirdServerDTO = ThirdServerDTO.builder()
                            .host(event.getHost())
                            .port(event.getPort())
                            .path(event.getPath())
                            .eventId(event.getEventId())
                            .build();
                    break;
            }

            eventHierarchy.add(EventDTO.builder()
                    .eventId(event.getEventId())
                    .dataKey(event.getDataKey())
                    .devId(devId)
                    .priority(event.getPriority())
                    .outputType(event.getOutputType())
                    .ruleType(event.getRuleType())
                    .ruleValue(event.getRuleValue())
                    .notifyBoxDTO(notifyBoxDTO)
                    .thirdServerDTO(thirdServerDTO)
                    .controlDTO(controlDTO)
                    .build());
        }

        return eventHierarchy;
    }
}
