package org.icslab.sibadev.devices.device.services;

import org.icslab.sibadev.common.constants.SkillServer;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.deployset.BoxDTO;
import org.icslab.sibadev.devices.device.domain.deployset.ButtonDTO;
import org.icslab.sibadev.devices.device.domain.deployset.DerivationDTO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TextBoxGraphDeployService {

    @Autowired
    private DeviceMapper deviceMapper;

    public ResponseDTO deploy(String authKey){

        return this.sendToSkillServer(SkillServer.SKILL_SERVER_HOST+SkillServer.TEXTBOX_GRAPH_DEPLOY_URL, new Object(){
            DeviceDTO device = deviceMapper.getDevice(authKey);

            List<BoxDTO> boxDTOList = deviceMapper.getBoxes(authKey);

            List<ButtonDTO> buttonDTOList = deviceMapper.getButtons(authKey);

            List<DerivationDTO> derivationDTOList = deviceMapper.getDerivations(authKey);
        });
    }

    private ResponseDTO sendToSkillServer(String url, Object dataset){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, dataset, ResponseDTO.class);
    }
}
