package org.icslab.sibadev.devices.vhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VirtualHubVO {

    String hubName;

    Integer userId;

    Integer hubId;
}
