package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.test.domain.ButtonVO;
import org.icslab.sibadev.devices.test.domain.TestLogDTO;
import org.icslab.sibadev.devices.test.domain.TextBoxVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestMapper {

    TextBoxVO getTextBox(
            @Param("devId")
            Integer devId,
            @Param("boxId")
            int boxId
    );

    List<ButtonVO> getTextButton(
            @Param("devId")
            Integer devId,
            @Param("boxId")
            int boxId
    );

    void addDeviceTestLog(TestLogDTO testLogDTO);

    void changeTestLogStatus(
            @Param("testId")
            int testId,
            @Param("testId")
            Character status
    );

    void changeTestLog(TestLogDTO testLogDTO);
}
