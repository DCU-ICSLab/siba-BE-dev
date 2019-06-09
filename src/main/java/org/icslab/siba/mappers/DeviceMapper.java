package org.icslab.siba.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.icslab.siba.devices.device.domain.DeviceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeviceMapper {

    List<DeviceVO> getDevice(Long userId);
}
