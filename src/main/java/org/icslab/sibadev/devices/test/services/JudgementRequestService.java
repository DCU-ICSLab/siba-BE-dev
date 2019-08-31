package org.icslab.sibadev.devices.test.services;

import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.test.domain.*;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubHostVO;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JudgementRequestService {

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private DeviceBoxSearchSerivce deviceBoxSearchSerivce;

    @Autowired
    private DeviceMapper deviceMapper;

    public ResponseDTO judgementRequest(TextBoxDTO textBoxDTO, Integer devId, String dynamic){

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(HttpStatus.OK)
                .data(null)
                .build();

        /*String dynamic = null;
        if(testSetDTO!=null){
            List<CommandDTO> command = testSetDTO.getCmdList();
            if(command.size()>0){
                for(OptionalDataDTO opt : command.get(command.size()-1).getAdditional()){
                    if(opt.getType().equals("2")){
                        dynamic = (String)opt.getValue();
                    }
                }
            }
        }*/

        TextBoxDTO returnBox = null;
        RestTemplate restTemplate = new RestTemplate();

        returnBox = this.request(textBoxDTO, devId, restTemplate, dynamic);
        try{
            while (returnBox!=null && returnBox.getBoxType()==7){
                returnBox = this.request(returnBox, devId, restTemplate, null);
            }
        }
        catch (Exception e){
            return responseDTO;
        }

        responseDTO = ResponseDTO.builder()
                .status(HttpStatus.OK)
                .data(returnBox)
                .build();

        return responseDTO;
    }

    private TextBoxDTO request(TextBoxDTO textBoxDTO, Integer devId, RestTemplate restTemplate, String dynamic){
        try{

            VirtualHubHostVO virtualHubHostVO = virtualHubMapper.getVirtualHubHostInfoByDevId(devId);
            String hubHost = virtualHubHostVO.getHost();
            Integer port = virtualHubHostVO.getPort();

            String convertStatement = textBoxDTO.getPreText().replaceAll("[$]", "");

            if(dynamic!=null){
                System.out.println(dynamic);
                convertStatement = convertStatement.replaceAll("[#]\\{dynamic\\}", dynamic);
                System.out.println(convertStatement);
            }

            //허브에게 명령어 셋 전송
            TestResponseDTO testResponseDTO = restTemplate.postForObject(
                    "http://" + hubHost + ":" + port + "/dev/" + deviceMapper.getDevMacWithDevId(devId) + "/judge",
                    JudgeStatementDTO.builder()
                            .statement(convertStatement)
                            .build(),
                    TestResponseDTO.class);

            //judge값이 true라면
            int index = 1;
            if(HttpStatus.valueOf(testResponseDTO.getStatus())==HttpStatus.OK){
                index = 0;
            }

            Integer cboxId = textBoxDTO.getButtons().get(index).getCboxId();
            if(cboxId!=null){
                textBoxDTO = deviceBoxSearchSerivce.search(devId, cboxId);
            }
            else
                textBoxDTO = null;
        }
        catch(Exception e){
            throw e;
        }

        return textBoxDTO;
    }
}
