package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DataModelMapper {

    List<DataModelDTO> getDataModel(
        @Param("devId")
        Integer devId,
        @Param("modType")
        String modType
    );

    void insertDataModel(DataModelDTO dataModelDTO);
}
