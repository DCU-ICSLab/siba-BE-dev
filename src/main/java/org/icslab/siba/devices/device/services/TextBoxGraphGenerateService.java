package org.icslab.siba.devices.device.services;

import lombok.Getter;
import lombok.Setter;
import org.icslab.siba.devices.device.domain.*;
import org.icslab.siba.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextBoxGraphGenerateService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Getter
    @Setter
    class Counter{
        private Integer value;

        Counter(){
            this.value=0;
        }
    }

    public TextBoxGraphDTO generate(String authKey) {

        //device information
        DeviceVO deviceVO = deviceMapper.getDevice(authKey);

        //source
        List<TextBoxVO> textBoxVOList = deviceMapper.getBox(authKey);
        List<BtnDerivationJoinVO> btnDerivationJoinVOList = deviceMapper.getBtnAndDerivation(authKey);

        //target
        List<TextBoxDTO> textBoxDTOList = new ArrayList<>();
        List<LinkerDTO> linkerDTOList = new ArrayList<>();

        Counter counter = new Counter();

        for (TextBoxVO textBox : textBoxVOList) {
            textBoxDTOList.add(
                    TextBoxDTO.builder()
                            .id(textBox.getBoxId())
                            .pos(new Object() {
                                double top = textBox.getYAxis();
                                double left = textBox.getXAxis();
                            })
                            .type(textBox.getBoxType())
                            .preorder(textBox.getPreText())
                            .postorder(textBox.getPostText())
                            .parentBox(new Object() {
                                int parentId = textBox.getPBoxId();
                                int code = textBox.getEvCode();
                            })
                            .info(this.getAdditionalInfo(
                                    textBox.getBoxType()
                                    ,btnDerivationJoinVOList
                                    ,textBox.getBoxId()
                                    ,counter
                                    ,linkerDTOList))
                            .linked(false)
                            .linking(false)
                            .build()
            );
        }

        return TextBoxGraphDTO.builder()
                .blockIdCounter(deviceVO.getBoxIdCnt())
                .codeIdCounter(deviceVO.getCodeCnt())
                .haveEntry(deviceVO.getHaveEntry())
                .pallet(textBoxDTOList)
                .linkers(linkerDTOList)
                .build();
    }

    private Object getAdditionalInfo(int boxType , List<BtnDerivationJoinVO> list, int boxId, Counter counter, List<LinkerDTO> linkerList) {
        Object info = null;
        switch (boxType) {
            case 1:
                info = new Object(){
                    List<ButtonDTO> buttons = getButtonsInfo(list, boxId, counter, linkerList);
                };
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

            LinkerDTO tempLinker = LinkerDTO.builder()
                    .childId(btn.getCBoxId())
                    .parentId(btn.getBoxId())
                    .code(btn.getEvCode())
                    .m(PositionDTO.builder()
                        .x(btn.getMx())
                        .y(btn.getMy())
                        .build()
                    )
                    .z(PositionDTO.builder()
                        .x(btn.getMx())
                        .y(btn.getMy())
                        .build()
                    )
                    .build();

            linkerList.add(tempLinker);

            buttonList.add(
                ButtonDTO.builder()
                    .code(btn.getEvCode())
                    .name(btn.getBtnName())
                    .linker(tempLinker)
                    .build()
            );
        }
        return buttonList;
    }
}
