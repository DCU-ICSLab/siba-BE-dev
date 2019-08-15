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

    public TextBoxDTO search(Integer devId, Integer boxId){
        TextBoxVO textBoxVO = testMapper.getTextBox(devId, boxId);
        List<ButtonVO> buttonVOS = testMapper.getTextButton(devId, boxId);

        String preText =textBoxVO.getPreText();
        String postText =textBoxVO.getPostText();

        //textBoxVO의 데이터가 아무 것도 없다면 Null pointer exception발생
        return TextBoxDTO.builder()
                .boxId(textBoxVO.getBoxId())
                .preText(preText)
                .postText(postText)
                .boxType(textBoxVO.getBoxType())
                .buttons(buttonVOS)
                .build();
    }
}
