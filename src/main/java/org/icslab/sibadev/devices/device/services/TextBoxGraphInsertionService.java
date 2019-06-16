package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxGraphDTO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextBoxGraphInsertionService {

    @Autowired
    DeviceMapper deviceMapper;

    public void insertion(TextBoxGraphDTO textBoxGraphDTO){

        //dev의 box_id_cnt, code_cnt, have_entry update
        deviceMapper.updateDevice(
            DeviceDTO.builder()
                .authKey(textBoxGraphDTO.getDevAuthKey())
                .boxIdCnt(textBoxGraphDTO.getBlockIdCounter())
                .codeCnt(textBoxGraphDTO.getCodeIdCounter())
                .build()
        );
    }
}
