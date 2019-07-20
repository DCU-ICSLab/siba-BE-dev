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
    private DeviceMapper deviceMapper;

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
    }

    private List<ButtonWrapperDTO> getAllButtons(TextBoxGraphDTO textBoxGraphDTO) {
        List<ButtonWrapperDTO> list = new ArrayList<>();
        for (TextBoxDTO box : textBoxGraphDTO.getPallet()) {
            for (ButtonDTO button : box.getInfo().getButtons()) {
                list.add(ButtonWrapperDTO.builder()
                        .boxId(box.getId())
                        .buttonDTO(button)
                        .build());
            }
        }
        return list;
    }
}
