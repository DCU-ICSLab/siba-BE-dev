package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.icslab.sibadev.devices.device.domain.BtnDerivationJoinVO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.TextBoxVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeviceMapper {

    DeviceDTO getDevice(String authKey);

    List<DeviceDTO> getDevices(Long userId);

    List<BtnDerivationJoinVO> getBtnAndDerivation(String authKey);

    List<TextBoxVO> getBox(String authKey);

    void updateDevice(DeviceDTO deviceDTO);

    //void multipleTableMultipleInsert();
}
