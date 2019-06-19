package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.device.domain.BtnDerivationJoinVO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.DeviceShortDTO;
import org.icslab.sibadev.devices.device.domain.TextBoxVO;
import org.icslab.sibadev.devices.device.domain.deployset.BoxDTO;
import org.icslab.sibadev.devices.device.domain.deployset.ButtonDTO;
import org.icslab.sibadev.devices.device.domain.deployset.DerivationDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.ButtonWrapperDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.LinkerDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeviceMapper {

    DeviceDTO getDevice(String authKey);

    List<DeviceShortDTO> getDevices(Long userId);

    List<BoxDTO> getBoxes(String authKey);

    List<ButtonDTO> getButtons(String authKey);

    List<DerivationDTO> getDerivations(String authKey);

    List<BtnDerivationJoinVO> getBtnAndDerivation(String authKey);

    List<TextBoxVO> getBoxAndDerivation(String authKey);

    void updateDevice(DeviceDTO deviceDTO);

    void deleteTextBoxes(String authKey);

    void deleteDerivations(String authKey);

    void deleteButtons(String authKey);

    void insertTextBoxes(
            @Param("textBoxDTOList")
            List<TextBoxDTO> textBoxDTOList,
            @Param("authKey")
            String authKey);

    void insertButtons(
            @Param("buttonDTOList")
            List<ButtonWrapperDTO> buttonDTOList,
            @Param("authKey")
            String authKey);

    void insertLinkers(
            @Param("linkerDTOList")
            List<LinkerDTO> linkerDTOList,
            @Param("authKey")
            String authKey);

    //void multipleTableMultipleInsert();
}
