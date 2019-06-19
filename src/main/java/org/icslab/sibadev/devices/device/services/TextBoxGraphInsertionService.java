package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.ButtonDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.ButtonWrapperDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxGraphDTO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextBoxGraphInsertionService {

    @Autowired
    DeviceMapper deviceMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insertion(TextBoxGraphDTO textBoxGraphDTO){

        //derivation ->btn ->box 순으로 삭제
        deviceMapper.deleteDerivations(textBoxGraphDTO.getDevAuthKey());
        deviceMapper.deleteButtons(textBoxGraphDTO.getDevAuthKey());
        deviceMapper.deleteTextBoxes(textBoxGraphDTO.getDevAuthKey());

        //dev의 box_id_cnt, code_cnt, have_entry update
        deviceMapper.updateDevice(
            DeviceDTO.builder()
                .authKey(textBoxGraphDTO.getDevAuthKey())
                .boxIdCnt(textBoxGraphDTO.getBlockIdCounter())
                .codeCnt(textBoxGraphDTO.getCodeIdCounter())
                .evCodeCnt(textBoxGraphDTO.getEventCodeIdCounter())
                .haveEntry(textBoxGraphDTO.getHaveEntry())
                .build()
        );

        //box ->btn ->derivation 순으로 삽입
        this.multipleTableMultipleInsert(textBoxGraphDTO);
    }

    private void multipleTableMultipleInsert(TextBoxGraphDTO textBoxGraphDTO){
        deviceMapper.insertTextBoxes(textBoxGraphDTO.getPallet(), textBoxGraphDTO.getDevAuthKey());
        deviceMapper.insertButtons(this.getAllButtons(textBoxGraphDTO), textBoxGraphDTO.getDevAuthKey());
        deviceMapper.insertLinkers(textBoxGraphDTO.getLinkers(), textBoxGraphDTO.getDevAuthKey());
    }

    private List<ButtonWrapperDTO> getAllButtons(TextBoxGraphDTO textBoxGraphDTO){
        List<ButtonWrapperDTO> list = new ArrayList<>();
        for(TextBoxDTO box : textBoxGraphDTO.getPallet()){
            for(ButtonDTO button : box.getInfo().getButtons()){
                list.add(ButtonWrapperDTO.builder()
                        .boxId(box.getId())
                        .buttonDTO(button)
                        .build());
            }
        }
        return list;
    }
}
