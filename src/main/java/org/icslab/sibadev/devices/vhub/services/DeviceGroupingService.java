package org.icslab.sibadev.devices.vhub.services;

import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceGroupingService {

    public List<VirtualHubDTO> grouping(List<DeviceDTO> deviceList){
        List<VirtualHubDTO> hubInfo = new ArrayList<>();

        int vhubId = -1;
        List<DeviceDTO> devices = null;
        for(DeviceDTO item: deviceList){
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
