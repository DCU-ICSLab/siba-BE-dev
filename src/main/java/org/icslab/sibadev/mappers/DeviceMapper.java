package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.device.domain.*;
import org.icslab.sibadev.devices.device.domain.deployset.BoxDTO;
import org.icslab.sibadev.devices.device.domain.deployset.ButtonDTO;
import org.icslab.sibadev.devices.device.domain.deployset.DerivationDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.ButtonWrapperDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.LinkerDTO;
import org.icslab.sibadev.devices.device.domain.textboxgraph.TextBoxDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DeviceMapper {

    DeviceDTO getDevice(Integer devId);

    Integer getRepositoryOwner(String devKey);

    Integer getDevIdWithDevMac(String devMac);

    String getBtnName(
            @Param("eventCode")
            Integer eventCode,
            @Param("devId")
            Integer devId
    );

    List<DeviceShortDTO> getDeviceAndHub(Long userId);

    List<DeviceDTO> getDevices(Long userId);

    List<BoxDTO> getBoxes(Integer devId);

    List<ButtonDTO> getButtons(Integer devId);

    List<DerivationDTO> getDerivations(Integer devId);

    List<BtnDerivationJoinVO> getBtnAndDerivation(Integer devId);

    List<TextBoxVO> getBoxAndDerivation(Integer devId);

    List<ConnectedDeviceVO> getConnectedDeviceInfo(Integer devId);

    void createDevice(Map<String, Object> devSet);

    void updateDevice(DeviceDTO deviceDTO);

    void deleteTextBoxes(Integer devId);

    void deleteDerivations(Integer devId);

    void deleteButtons(Integer devId);

    void deleteConnectedDevice(String devMac);

    void deleteConnectedDeviceById(Integer devId);

    void clearAllConnectedDevice();

    void createConnectedDevice(@Param("devId") Integer devId, @Param("devMac") String devMac);

    Integer findDevId(String devKey);

    void insertTextBoxes(
            @Param("textBoxDTOList")
            List<TextBoxDTO> textBoxDTOList,
            @Param("devId")
            Integer devId);

    void insertButtons(
            @Param("buttonDTOList")
            List<ButtonWrapperDTO> buttonDTOList,
            @Param("devId")
            Integer devId);

    void insertLinkers(
            @Param("linkerDTOList")
            List<LinkerDTO> linkerDTOList,
            @Param("devId")
            Integer devId);

    List<BoxRuleVO> getBoxAndRule(
            @Param("devId")
            Integer devId,
            @Param("boxType")
            String boxType);

    void createStateRule(Map<String,Object> map);

    //void multipleTableMultipleInsert();
}
