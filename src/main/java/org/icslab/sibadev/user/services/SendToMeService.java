package org.icslab.sibadev.user.services;

import org.icslab.sibadev.user.domain.KakaoTokenDetailDTO;
import org.icslab.sibadev.user.domain.SendToMeResponse;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SendToMeService {

    private static final String SEND_TO_ME_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

    private RestTemplate restTemplate;

    public SendToMeService(){
        this.restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJackson2CborHttpMessageConverter());
    }

    public void sendToMe(KakaoTokenDetailDTO kakaoTokenDetailDTO){
        System.out.println("detail: "+kakaoTokenDetailDTO.getAccess_token());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Authorization", "Bearer " + kakaoTokenDetailDTO.getAccess_token());
        headers.add("Content-Type", "application/json");

        HttpEntity<Object> request = new HttpEntity(new Object(){
            public Object template_object = new Object(){
                public String object_type ="text";
                public String text ="1234";
                public String link ="www.naver.com";
            };
        },headers);

        restTemplate.postForObject(SEND_TO_ME_URL, request, SendToMeResponse.class);
    }
}
