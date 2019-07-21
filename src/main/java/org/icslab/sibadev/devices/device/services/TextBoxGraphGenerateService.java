package org.icslab.sibadev.devices.device.services;

import lombok.Getter;
import lombok.Setter;
import org.icslab.sibadev.devices.device.domain.BtnDerivationJoinVO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.TextBoxVO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.*;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextBoxGraphGenerateService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private TestMapper testMapper;

    @Getter
    @Setter
    class Counter{
        private Integer value;

        Counter(){
            this.value=0;
        }
    }

    public TextBoxGraphDTO generate(Integer devId) {

        //device information
        DeviceDTO deviceDTO = deviceMapper.getDevice(devId);

        //source
        List<TextBoxVO> textBoxVOList = deviceMapper.getBoxAndDerivation(devId);
        List<BtnDerivationJoinVO> btnDerivationJoinVOList = deviceMapper.getBtnAndDerivation(devId);

        //target
        List<TextBoxDTO> textBoxDTOList = new ArrayList<>();
        List<LinkerDTO> linkerDTOList = new ArrayList<>();

        Counter counterChild = new Counter();
        Counter counterParent = new Counter();

        Integer prevBoxId = -1;
        for (TextBoxVO textBox : textBoxVOList) {
            Integer curBoxId = textBox.getBoxId();
            if(prevBoxId.equals(curBoxId)) continue;
            prevBoxId = curBoxId;
            textBoxDTOList.add(
                    TextBoxDTO.builder()
                            .id(textBox.getBoxId())
                            .pos(PositionDTO.builder()
                                .x(textBox.getXAxis())
                                .y(textBox.getYAxis())
                                .build()
                            )
                            .type(textBox.getBoxType())
                            .preorder(textBox.getPreText())
                            .postorder(textBox.getPostText())
                            .parentBox(this.getParentBoxesInfo(textBoxVOList, textBox.getBoxId(), counterParent))
                            .info(this.getAdditionalInfo(
                                    textBox.getBoxType()
                                    ,btnDerivationJoinVOList
                                    ,textBox.getBoxId()
                                    ,counterChild
                                    ,linkerDTOList))
                            .linked(false)
                            .linking(false)
                            .headRow(textBox.getHeadRow())
                            .footRow(textBox.getFootRow())
                            .build()
            );
        }

        return TextBoxGraphDTO.builder()
                .devId(deviceDTO.getDevId())
                .devAuthKey(deviceDTO.getAuthKey())
                .vHubId(deviceDTO.getVHubId())
                .devName(deviceDTO.getDevName())
                .blockIdCounter(deviceDTO.getBoxIdCnt())
                .codeIdCounter(deviceDTO.getCodeCnt())
                .eventCodeIdCounter(deviceDTO.getEvCodeCnt())
                .haveEntry(deviceDTO.getHaveEntry())
                .pallet(textBoxDTOList)
                .linkers(linkerDTOList)
                .testLogList(testMapper.getTestLogList(deviceDTO.getDevId()))
                .build();
    }

    private InfoDTO getAdditionalInfo(int boxType , List<BtnDerivationJoinVO> list, int boxId, Counter counter, List<LinkerDTO> linkerList) {
        InfoDTO info = null;
        switch (boxType) {
            case 1:
            case 2:
            case 3:
            case 5:
                info = InfoDTO.builder()
                        .buttons(getButtonsInfo(list, boxId, counter, linkerList))
                        .build();
                break;
            default:
                break;
        }
        return info;
    }

    private List<ButtonDTO> getButtonsInfo(List<BtnDerivationJoinVO> list, int boxId, Counter counter, List<LinkerDTO> linkerList){
        List<ButtonDTO> buttonList = new ArrayList<>();
        for(int i = counter.getValue(); i < list.size(); i++) {
            BtnDerivationJoinVO btn = list.get(i);

            //boxId가 같지 않다면 루프 탈출
            if(btn.getBoxId()!=boxId){
                counter.setValue(i);
                break;
            }

            LinkerDTO tempLinker = null;
            if(btn.getCBoxId()!=null){
                tempLinker = LinkerDTO.builder()
                        .childId(btn.getCBoxId())
                        .parentId(btn.getBoxId())
                        .code(btn.getBtnCode())
                        .m(PositionDTO.builder()
                                .x(btn.getMx())
                                .y(btn.getMy())
                                .build()
                        )
                        .z(PositionDTO.builder()
                                .x(btn.getZx())
                                .y(btn.getZy())
                                .build()
                        )
                        .build();

                linkerList.add(tempLinker);
            }

            buttonList.add(
                ButtonDTO.builder()
                    .code(btn.getBtnCode())
                    .name(btn.getBtnName())
                    .idx(btn.getIdx())
                    .type(btn.getType())
                    .eventCode(btn.getEventCode())
                    .isSpread(btn.getIsSpread())
                    .linker(tempLinker)
                    .build()
            );
        }
        return buttonList;
    }

    private List<ParentBoxDTO> getParentBoxesInfo(List<TextBoxVO> textBoxVOList, int boxId, Counter counter){
        List<ParentBoxDTO> parentBoxes = new ArrayList<>();

        for(int i=counter.getValue(); i < textBoxVOList.size(); i++){
            TextBoxVO box = textBoxVOList.get(i);

            if(box.getPBoxId()==null){
                counter.setValue(i+1);
                break;
            }

            //boxId가 같지 않다면 루프 탈출
            if(box.getBoxId()!=boxId){
                counter.setValue(i);
                break;
            }

            parentBoxes.add(
                ParentBoxDTO.builder()
                    .parentId(box.getPBoxId())
                    .code(box.getBtnCode())
                    .build()
            );
        }

        return parentBoxes;
    }
}
