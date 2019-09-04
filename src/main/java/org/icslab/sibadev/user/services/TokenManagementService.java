package org.icslab.sibadev.user.services;

import org.icslab.sibadev.user.domain.KakaoTokenDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenManagementService {

    @Autowired
    private SendToMeService sendToMeService;

    public void manage(KakaoTokenDetailDTO kakaoTokenDetailDTO){
        System.out.println(kakaoTokenDetailDTO);
        //sendToMeService.sendToMe(kakaoTokenDetailDTO);
    }
}
