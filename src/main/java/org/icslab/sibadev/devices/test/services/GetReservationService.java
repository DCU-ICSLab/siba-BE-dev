package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.devices.test.domain.*;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubHostVO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GetReservationService {

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private DeviceMapper deviceMapper;


    public TextBoxDTO getReservationForHub(String devMac, Integer vhubId){

        VirtualHubHostVO virtualHubHostVO = virtualHubMapper.getVirtualHubHostInfo(vhubId);
        String hubHost = virtualHubHostVO.getHost();
        Integer port = virtualHubHostVO.getPort();

        RestTemplate restTemplate = new RestTemplate();
        ReservationListDTO reservationListDTO = restTemplate.getForObject("http://" + hubHost + ":" + port + "/hub/" + devMac+"/reservation", ReservationListDTO.class);

        List<ButtonVO> buttonVOList = new ArrayList<>();
        int index=0;
        for(ReservationDTO item : reservationListDTO.getReservationList()){
            Date date = new Date(item.getActAt());
            SimpleDateFormat df2 = new SimpleDateFormat("MM/dd-HH:mm");
            buttonVOList.add(new ButtonVO(index++, deviceMapper.getBtnName(item.getEventCode())+" ("+df2.format(date)+")", item.getResId(), "6", null));
        }

        String preText = reservationListDTO.getReservationList().size()==0 ? "예약된 명령이 존재하지 않습니다." : "예약된 명령들 입니다. ("+reservationListDTO.getReservationList().size()+"개)";
        String postText = reservationListDTO.getReservationList().size()==0 ? null : "예약 취소를 원하신다면 선택하세요.";
        return TextBoxDTO.builder()
                .preText(preText)
                .buttons(buttonVOList)
                .boxType(1)
                .boxId(-1)
                .postText(postText)
                .build();
    }
}
