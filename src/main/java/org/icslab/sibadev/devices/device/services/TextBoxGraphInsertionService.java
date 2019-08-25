package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.StateRuleDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.*;
import org.icslab.sibadev.mappers.DataModelMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TextBoxGraphInsertionService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Autowired
    TextBoxGraphDeletionService textBoxGraphDeletionService;

    @Transactional(rollbackFor = Exception.class)
    public void insertion(TextBoxGraphDTO textBoxGraphDTO) {
        textBoxGraphDeletionService.delete(textBoxGraphDTO.getDevId());

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .devId(textBoxGraphDTO.getDevId())
                .authKey(textBoxGraphDTO.getDevAuthKey())
                .boxIdCnt(textBoxGraphDTO.getBlockIdCounter())
                .codeCnt(textBoxGraphDTO.getCodeIdCounter())
                .evCodeCnt(textBoxGraphDTO.getEventCodeIdCounter())
                .haveEntry(textBoxGraphDTO.getHaveEntry())
                .build();

        //dev의 box_id_cnt, code_cnt, have_entry update
        deviceMapper.updateDevice(deviceDTO);

        //box ->btn ->derivation 순으로 삽입
        this.multipleTableMultipleInsert(textBoxGraphDTO);
    }

    private void multipleTableMultipleInsert(TextBoxGraphDTO textBoxGraphDTO) {
        deviceMapper.insertTextBoxes(textBoxGraphDTO.getPallet(), textBoxGraphDTO.getDevId());

        deviceMapper.insertButtons(this.getAllButtons(textBoxGraphDTO), textBoxGraphDTO.getDevId());

        if(textBoxGraphDTO.getLinkers().size()>0)
            deviceMapper.insertLinkers(textBoxGraphDTO.getLinkers(), textBoxGraphDTO.getDevId());

        List<StateRuleDTO> rules = this.extractRules(textBoxGraphDTO.getPallet());
        if(rules.size()>0)
            dataModelMapper.insertAllRules(rules);
    }

    private List<ButtonWrapperDTO> getAllButtons(TextBoxGraphDTO textBoxGraphDTO) {

        List<ButtonWrapperDTO> buttonList = new ArrayList<>();
        for (TextBoxDTO box : textBoxGraphDTO.getPallet()) {
            for (ButtonDTO button : box.getInfo().getButtons()) {
                buttonList.add(ButtonWrapperDTO.builder()
                        .boxId(box.getId())
                        .buttonDTO(button)
                        .build());
            }
        }
        return buttonList;
    }

    //rules를 추출
    private List<StateRuleDTO> extractRules(List<TextBoxDTO> textBoxDTOS){
        List<StateRuleDTO> list = new ArrayList<>();
        for (TextBoxDTO textBoxDTO: textBoxDTOS) {
            //select box라면
            if(textBoxDTO.getType()==6 && textBoxDTO.getRules()!=null){
                list.addAll(textBoxDTO.getRules());
            }
        }
        return list;
    }
}
