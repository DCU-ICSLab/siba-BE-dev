package org.icslab.sibadev.devices.vhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VirtualHubHostVO {

    private String host;

    private Integer port;
}
