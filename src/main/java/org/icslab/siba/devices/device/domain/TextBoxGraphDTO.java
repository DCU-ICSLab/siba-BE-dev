package org.icslab.siba.devices.device.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class TextBoxGraphDTO {

    private int blockIdCounter;

    private int codeIdCounter;

    private boolean haveEntry;

    private List<TextBoxDTO> pallet;

    private List<LinkerDTO> linkers;
}
