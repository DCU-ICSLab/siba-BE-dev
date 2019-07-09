package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VirtualHubMapper {

    void createHub(@Param("userId") Long userId, @Param("hub") VirtualHubDTO virtualHubDTO);

    VirtualHubVO getHubOwner(String hubKey);
}
