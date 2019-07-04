package org.icslab.sibadev.devices.device.domain;

import lombok.Data;

@Data
public class TextBoxVO {

    private int boxId;

    private String preText;

    private String postText;

    private int boxType;

    private double xAxis; //x axis

    private double yAxis; //y axis

    private Integer headRow;

    private Integer footRow;

    private Integer pBoxId;

    private Integer btnCode;
}
