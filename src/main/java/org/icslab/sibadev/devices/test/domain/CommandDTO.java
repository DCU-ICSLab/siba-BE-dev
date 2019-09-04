package org.icslab.sibadev.devices.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommandDTO {

    private String btnType;

    private Integer eventCode;

    private List<OptionalDataDTO> additional;
}
