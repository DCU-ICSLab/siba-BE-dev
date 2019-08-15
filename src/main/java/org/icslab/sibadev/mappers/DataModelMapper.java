package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.device.domain.StateRuleDTO;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelDTO;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelVO;
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

    List<DataModelVO> getDataModelWithKey(Integer devId);

    List<StateRuleDTO> getAllRules(
            @Param("devId")
            Integer devId,
            @Param("boxId")
            Integer boxId);

    void deleteRule(Integer devId);

    void insertAllRules(
            @Param("rules")
            List<StateRuleDTO> rules);
}
