package org.icslab.sibadev.devices.device.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class ConnectedDeviceVO {

    private String devMac;

    private Timestamp connectedAt;
}
