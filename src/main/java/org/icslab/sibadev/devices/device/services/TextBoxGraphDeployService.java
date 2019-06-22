package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.common.constants.SkillServer;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.deployset.BoxDTO;
import org.icslab.sibadev.devices.device.domain.deployset.ButtonDTO;
import org.icslab.sibadev.devices.device.domain.deployset.DeployDTO;
import org.icslab.sibadev.devices.device.domain.deployset.DerivationDTO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TextBoxGraphDeployService {

    @Autowired
    private DeviceMapper deviceMapper;

    public ResponseDTO deploy(String authKey){

        return this.sendToSkillServer(
                SkillServer.SKILL_SERVER_HOST+SkillServer.TEXTBOX_GRAPH_DEPLOY_URL,
                DeployDTO.builder()
                        .device(deviceMapper.getDevice(authKey))
                        .boxDTOList(deviceMapper.getBoxes(authKey))
                        .buttonDTOList(deviceMapper.getButtons(authKey))
                        .derivationDTOList(deviceMapper.getDerivations(authKey))
                        .build()
        );
    }

    private ResponseDTO sendToSkillServer(String url, DeployDTO dataset){
        System.out.println(dataset);
        RestTemplate restTemplate = new RestTemplate();
        /*List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);*/

        /*MappingJacksonValue value = new MappingJacksonValue(dataset);
        HttpEntity<MappingJacksonValue> entity = new HttpEntity<>(value);
        value.setSerializationView(DeployDTO.class);*/
        return restTemplate.postForObject(url, dataset, ResponseDTO.class);

        //ResponseEntity<ResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST,requestEntity, ResponseDTO.class);
        //return response.getBody();
    }
}
