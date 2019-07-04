package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextBoxGraphDeletionService {

    @Autowired
    private DeviceMapper deviceMapper;

    public void delete(Integer devId){
        //derivation ->btn ->box 순으로 삭제
        deviceMapper.deleteDerivations(devId);
        deviceMapper.deleteButtons(devId);
        deviceMapper.deleteTextBoxes(devId);
    }
}
