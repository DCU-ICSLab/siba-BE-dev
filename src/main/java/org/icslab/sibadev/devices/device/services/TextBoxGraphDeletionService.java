package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.mappers.DataModelMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextBoxGraphDeletionService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DataModelMapper dataModelMapper;

    public void delete(Integer devId){
        //res_box_crs, derivation ->btn ->box 순으로 삭제
        dataModelMapper.deleteRule(devId);
        deviceMapper.deleteDerivations(devId);
        deviceMapper.deleteButtons(devId);
        deviceMapper.deleteTextBoxes(devId);
    }
}
