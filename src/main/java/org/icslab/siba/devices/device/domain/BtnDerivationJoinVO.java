package org.icslab.siba.devices.device.domain;

import lombok.Data;

@Data
public class BtnDerivationJoinVO {

    private int boxId;

    private int evCode;

    private String btnName;

    private int idx;

    private int dCode;

    private int cBoxId;

    private double mx;

    private double my;

    private double zx;

    private double zy;
}
