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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String,Object> map = ruleGrouping(rules);

        RestTemplate restTemplate = new RestTemplate();
        try{
            DeviceStateKeyListDTO states = restTemplate.postForObject(
                    "http://" + virtualHubHostVO.getHost() + ":" + virtualHubHostVO.getPort() + "/dev/" + devMac+"/state",
                    DeviceKeyListDTO.builder()
                            .keySet((List<String>)map.get("ruleList"))
                            .build(),
                    DeviceStateKeyListDTO.class);

            DeviceStateKeyListDTO conversionState = this.stateConversion(states, (List<RuleHierarchyDTO>) map.get("hierarchyDTOS"));

            return TextBoxDTO.builder()
                    .preText(convertText(textBoxVO.getPreText(), conversionState))
                    .buttons(new ArrayList<>())
                    .boxType(6)
                    .boxId(-1)
                    .postText(convertText(textBoxVO.getPostText(), conversionState))
                    .build();
        }
        catch (Exception e){
            throw e;
        }
    }

    private DeviceStateKeyListDTO stateConversion(DeviceStateKeyListDTO states, List<RuleHierarchyDTO> ruleHierarchyDTOS){
        int i=0;
        for (DeviceStateDTO key : states.getKeySet()){
            for (StateRuleDTO rule: ruleHierarchyDTOS.get(i).getRules()) {
                boolean isMatch = false;
                switch (rule.getRuleType()){
                    case "1":
                        isMatch=true;
                        break;
                    case "2":
                        if(key.getValue().equals(rule.getRuleValue())){
                            isMatch=true;
                            states.getKeySet().get(i).setValue(rule.getMapVal());
                        }
                        break;
                    case "3":
                        if(!key.getValue().equals(rule.getRuleValue())){
                            isMatch=true;
                            states.getKeySet().get(i).setValue(rule.getMapVal());
                        }
                        break;
                    case "4":
                        if(key.getValue().compareTo(rule.getRuleValue())>0){
                            isMatch=true;
                            states.getKeySet().get(i).setValue(rule.getMapVal());
                        }
                        break;
                    default:
                        if(key.getValue().compareTo(rule.getRuleValue())<0){
                            isMatch=true;
                            states.getKeySet().get(i).setValue(rule.getMapVal());
                        }
                        break;
                }
                if(isMatch) break;
            }
            i++;
        }
        return states;
    }

    private Map<String, Object> ruleGrouping(List<StateRuleDTO> rules){

        Map<String, Object> map =new HashMap<>();

        List<String> ruleList = new ArrayList<>();
        List<RuleHierarchyDTO> hierarchyDTOS = new ArrayList<>();
        String dataKey = "";
        for (StateRuleDTO rule: rules) {
            if(!rule.getDataKey().equals(dataKey)){
                dataKey = rule.getDataKey();
                ruleList.add(dataKey);
                RuleHierarchyDTO hierarchy = RuleHierarchyDTO.builder()
                        .dataKey(dataKey)
                        .rules(new ArrayList<>())
                        .build();
                hierarchy.getRules().add(rule);
                hierarchyDTOS.add(hierarchy);
            }
            else{
                hierarchyDTOS.get(hierarchyDTOS.size()-1).getRules().add(rule);
            }
        }

        map.put("ruleList", ruleList);
        map.put("hierarchyDTOS", hierarchyDTOS);
        return map;
    }

    private String convertText(String text, DeviceStateKeyListDTO states){
        for (DeviceStateDTO key : states.getKeySet()){
            System.out.println(key.getKey()+" "+key.getValue());
            text = text.replaceAll("#\\{"+key.getKey()+"}", key.getValue());
        }
        return text;
    }
}
