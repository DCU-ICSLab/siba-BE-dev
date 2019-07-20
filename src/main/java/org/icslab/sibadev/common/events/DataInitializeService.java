package org.icslab.sibadev.common.events;

import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializeService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @PostConstruct
    public void clearDataset(){
        System.out.println("clear all connected devices");
        virtualHubMapper.unlinkAllHub(); //연결되어 있던 허브 상태 모두 false로
        deviceMapper.clearAllConnectedDevice(); //연결된 모든 디바이스 제거
    }
}
