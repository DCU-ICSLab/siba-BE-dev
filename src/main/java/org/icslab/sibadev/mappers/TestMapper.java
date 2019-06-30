package org.icslab.sibadev.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.icslab.sibadev.devices.test.domain.ButtonVO;
import org.icslab.sibadev.devices.test.domain.TextBoxVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestMapper {

    TextBoxVO getTextBox(
            @Param("authKey")
            String authKey,
            @Param("boxId")
            int boxId
    );

    List<ButtonVO> getTextButton(
            @Param("authKey")
            String authKey,
            @Param("boxId")
            int boxId
    );
}
