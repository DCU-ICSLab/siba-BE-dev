package org.icslab.siba.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VirtualHubMapper {

    void createHub(Long userId);
}
