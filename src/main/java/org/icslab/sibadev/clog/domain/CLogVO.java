package org.icslab.sibadev.clog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class CLogVO {

    private Timestamp actTime;

    private Integer hubId;

    private String messageType;
}
