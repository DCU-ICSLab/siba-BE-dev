package org.icslab.siba.devices.device.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextBoxDTO {
    private Object pos;

    private int type;

    private String preorder;

    private String postorder;

    private Object parentBox;

    private int height;

    private int id;

    private boolean linking;

    private boolean linked;

    private Object info;
}

/*@Data
@Builder
class Position{
    private double top;

    private double left;
}

@Data
class ParentBox{
    private int parentId;

    private int code;
}*/