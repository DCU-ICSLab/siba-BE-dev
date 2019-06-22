package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextBoxGraphDeletionService {

    @Autowired
    private DeviceMapper deviceMapper;

    public void delete(String authKey){
        //derivation ->btn ->box 순으로 삭제
        deviceMapper.deleteDerivations(authKey);
        deviceMapper.deleteButtons(authKey);
        deviceMapper.deleteTextBoxes(authKey);
    }
}
