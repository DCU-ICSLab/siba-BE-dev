package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.device.domain.StateRuleDTO;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelDTO;
import org.icslab.sibadev.devices.device.domain.datamodel.DataModelVO;
import org.icslab.sibadev.devices.device.domain.event.ControlDTO;
import org.icslab.sibadev.devices.device.domain.event.EventVO;
import org.icslab.sibadev.devices.device.domain.event.NotifyBoxDTO;
import org.icslab.sibadev.devices.device.domain.event.ThirdServerDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DataModelMapper {

    List<DataModelDTO> getDataModel(
        @Param("devId")
        Integer devId,
        @Param("modType")
        String modType
    );

    List<DataModelDTO> getAllDataModel(Integer devId);

    void insertDataModel(DataModelDTO dataModelDTO);

    List<DataModelVO> getDataModelWithKey(Integer devId);

    List<StateRuleDTO> getAllRules(
            @Param("devId")
            Integer devId,
            @Param("boxId")
            Integer boxId);

    List<StateRuleDTO> getAllDeviceRules(Integer devId);

    void deleteRule(Integer devId);

    void insertAllRules(
            @Param("rules")
            List<StateRuleDTO> rules);

    void deleteRuleWithModId(Integer modId);

    List<EventVO> getAllEvents(Integer devId);

    void addEvent(Map<String, Object> map);

    void addNotifyBox(NotifyBoxDTO notifyBoxDTO);

    void addThirdServer(ThirdServerDTO thirdServerDTO);

    void addControl(ControlDTO controlDTO);

    void deleteEvent(Integer eventId);

    void deleteControl(Integer eventId);

    void deleteThirdServer(Integer eventId);

    void deleteNotifyBox(Integer eventId);
}
