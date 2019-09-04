package org.icslab.sibadev.devices.test.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DeviceStateKeyListDTO {

    private List<DeviceStateDTO> keySet;
}
