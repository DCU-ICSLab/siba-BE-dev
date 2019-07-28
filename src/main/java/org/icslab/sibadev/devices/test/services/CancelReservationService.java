package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.devices.test.domain.TestResponseDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubHostVO;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CancelReservationService {

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    public TestResponseDTO cancelRequest(Integer vhubId, Integer resId){
        VirtualHubHostVO virtualHubHostVO = virtualHubMapper.getVirtualHubHostInfo(vhubId);
        String hubHost = virtualHubHostVO.getHost();
        Integer port = virtualHubHostVO.getPort();

        //허브에게 명령 전송
        RestTemplate restTemplate = new RestTemplate();

        TestResponseDTO testResponseDTO = null;
        try{
            testResponseDTO = restTemplate.postForObject("http://" + hubHost + ":" + port + "/hub/reservation/" + resId, null, TestResponseDTO.class);
        }
        catch (Exception e){
            testResponseDTO = TestResponseDTO.builder()
                    .msg("허브까지 명령이 도달하지 못하였습니다.")
                    .status(200)
                    .build();
        }
        return testResponseDTO;
    }
}
