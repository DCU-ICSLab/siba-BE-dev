package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.common.constants.SkillServer;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.deployset.DeployDTO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TextBoxGraphDeployService {

    @Autowired
    private DeviceMapper deviceMapper;

    public ResponseDTO deploy(Integer devId){

        return this.sendToSkillServer(
                SkillServer.SKILL_SERVER_HOST+SkillServer.TEXTBOX_GRAPH_DEPLOY_URL,
                DeployDTO.builder()
                        .device(deviceMapper.getDevice(devId))
                        .boxDTOList(deviceMapper.getBoxes(devId))
                        .buttonDTOList(deviceMapper.getButtons(devId))
                        .derivationDTOList(deviceMapper.getDerivations(devId))
                        .build()
        );
    }

    private ResponseDTO sendToSkillServer(String url, DeployDTO dataset){
        System.out.println(dataset);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, dataset, ResponseDTO.class);
    }
}
