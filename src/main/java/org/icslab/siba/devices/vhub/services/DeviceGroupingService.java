package org.icslab.siba.devices.vhub.services;

import org.icslab.siba.devices.device.domain.DeviceVO;
import org.icslab.siba.devices.vhub.domain.VirtualHubDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceGroupingService {

    public List<VirtualHubDTO> grouping(List<DeviceVO> deviceList){
        List<VirtualHubDTO> hubInfo = new ArrayList();

        int vhubId = -1;
        List<DeviceVO> devices = null;
        for(DeviceVO item: deviceList){
            int curHubId = item.getVHubId();

            if(vhubId != curHubId){ //새로운 허브라면
                devices = new ArrayList<>();
                if(item.getAuthKey() !=null) devices.add(item);
                VirtualHubDTO  virtualHub = VirtualHubDTO.builder()
                        .vhubId(curHubId)
                        .devices(devices)
                        .build();
                hubInfo.add(virtualHub);
                continue;
            }

            devices.add(item);
        }

        return hubInfo;
    }
}
