package org.icslab.sibadev.devices.device.domain.textboxgraph;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TextBoxGraphDTO {

    private String devAuthKey;

    private Integer devId;

    private String devName;

    private int vHubId;

    private int blockIdCounter;

    private int codeIdCounter;

    private int eventCodeIdCounter;

    private Boolean haveEntry;

    private List<TextBoxDTO> pallet;

    private List<LinkerDTO> linkers;
}
