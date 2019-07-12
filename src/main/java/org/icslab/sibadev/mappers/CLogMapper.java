package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.clog.domain.CLogVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CLogMapper {

    void insertCLog(@Param("vhubId") Integer vhubId, @Param("msgType") String msgType);

    List<CLogVO> selectCLogs(Long userId);
}
