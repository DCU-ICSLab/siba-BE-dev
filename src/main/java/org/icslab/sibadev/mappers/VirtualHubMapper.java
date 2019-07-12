package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface VirtualHubMapper {

    void updateDeviceLinkHub(@Param("vhubId") Integer vhubId, @Param("devId") Integer devId);

    void createHub(Map<String, Object> map);

    VirtualHubVO getHubOwner(String hubKey);

    void updateHubStatus(@Param("hubKey") String hubKey, @Param("status") Boolean status);

    void updateDeviceUnlink(Integer devId);
}
