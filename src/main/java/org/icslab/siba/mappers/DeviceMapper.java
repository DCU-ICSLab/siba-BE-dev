package org.icslab.siba.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.icslab.siba.devices.device.domain.BtnDerivationJoinVO;
import org.icslab.siba.devices.device.domain.DeviceVO;
import org.icslab.siba.devices.device.domain.TextBoxVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeviceMapper {

    DeviceVO getDevice(String authKey);

    List<DeviceVO> getDevices(Long userId);

    List<BtnDerivationJoinVO> getBtnAndDerivation(String authKey);

    List<TextBoxVO> getBox(String authKey);
}
