package org.icslab.sibadev.clog.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CLogDTO {

    private Timestamp actTime;

    private Integer hubId;

    private Character messageType;
}
