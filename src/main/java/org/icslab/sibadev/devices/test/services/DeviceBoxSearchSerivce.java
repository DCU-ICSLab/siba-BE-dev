package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.devices.test.domain.ButtonVO;
import org.icslab.sibadev.devices.test.domain.TextBoxDTO;
import org.icslab.sibadev.devices.test.domain.TextBoxVO;
import org.icslab.sibadev.mappers.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceBoxSearchSerivce {

    @Autowired
    private TestMapper testMapper;

    public TextBoxDTO search(String authKey, int boxId){
        TextBoxVO textBoxVO = testMapper.getTextBox(authKey, boxId);
        List<ButtonVO> buttonVOS = testMapper.getTextButton(authKey, boxId);

        return TextBoxDTO.builder()
                .boxId(textBoxVO.getBoxId())
                .preText(textBoxVO.getPreText())
                .postText(textBoxVO.getPostText())
                .boxType(textBoxVO.getBoxType())
                .buttons(buttonVOS)
                .build();
    }
}
