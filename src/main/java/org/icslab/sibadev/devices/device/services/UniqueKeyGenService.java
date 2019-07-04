package org.icslab.sibadev.devices.device.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UniqueKeyGenService {

    //디바이스 키 값 발급을 위한 메서드, 추후 보강 필요
    public String generate(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
