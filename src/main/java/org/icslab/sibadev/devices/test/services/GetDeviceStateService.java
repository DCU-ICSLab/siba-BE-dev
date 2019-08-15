package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.devices.device.domain.StateRuleDTO;
import org.icslab.sibadev.devices.test.domain.*;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubHostVO;
import org.icslab.sibadev.mappers.DataModelMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.TestMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetDeviceStateService {

    @Autowired
    private DataModelMapper dataModelMapper;

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private TestMapper testMapper;

    public TextBoxDTO process(String devMac, Integer vhubId, Integer devId, Integer boxId){
        List<StateRuleDTO> rules = dataModelMapper.getAllRules(devId,boxId);
        TextBoxVO textBoxVO = testMapper.getTextBox(devId, boxId);

        VirtualHubHostVO virtualHubHostVO = virtualHubMapper.getVirtualHubHostInfo(vhubId);

        RestTemplate restTemplate = new RestTemplate();
        try{
            DeviceStateKeyListDTO states = restTemplate.postForObject(
                    "http://" + virtualHubHostVO.getHost() + ":" + virtualHubHostVO.getPort() + "/dev/" + devMac+"/state",
                    DeviceKeyListDTO.builder()
                            .keySet(ruleGrouping(rules))
                            .build(),
                    DeviceStateKeyListDTO.class);

            return TextBoxDTO.builder()
                    .preText(convertText(textBoxVO.getPreText(), states))
                    .buttons(new ArrayList<>())
                    .boxType(6)
                    .boxId(-1)
                    .postText(convertText(textBoxVO.getPostText(), states))
                    .build();
        }
        catch (Exception e){
            throw e;
        }
    }

    private List<String> ruleGrouping(List<StateRuleDTO> rules){

        List<String> ruleList = new ArrayList<>();
        List<RuleHierarchyDTO> hierarchyDTOS = new ArrayList<>();
        String dataKey = "";
        for (StateRuleDTO rule: rules) {
            if(!rule.getDataKey().equals(dataKey)){
                dataKey = rule.getDataKey();
                ruleList.add(dataKey);
            }
        }

        return ruleList;
    }

    private String convertText(String text, DeviceStateKeyListDTO states){
        for (DeviceStateDTO key : states.getKeySet()){
            text = text.replaceAll("#\\{"+key.getKey()+"}", key.getValue());
        }
        return text;
    }
}
