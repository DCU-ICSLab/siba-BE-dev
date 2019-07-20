package org.icslab.sibadev.devices.vhub;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubDTO;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class VirtualHubController {

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @PostMapping("/vhub")
    public ResponseDTO virtualHubCreate(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody VirtualHubDTO virtualHubDTO) {
        Long userId = userPrincipal.getId();

        //insert 이전 수행
        virtualHubDTO.setHubStatus(false);

        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("hub", virtualHubDTO);

        virtualHubMapper.createHub(map);

        //insert 이후 수행
        virtualHubDTO.setVhubId((int)map.get("vhubId"));
        virtualHubDTO.setDevices(new ArrayList<>());

        return ResponseDTO.builder()
                .msg("vhub creation")
                .data(virtualHubDTO)
                .status(HttpStatus.OK).build();
    }

    @PostMapping("/vhub/{vhubId}/devices")
    public ResponseDTO deviceRepoAdd(@PathVariable Integer vhubId, @RequestBody List<Integer> list) {
        for (Integer devId: list) {
            virtualHubMapper.updateDeviceLinkHub(vhubId, devId);
        }
        return ResponseDTO.builder()
                .msg("vhub repo insertion")
                .data(new Object(){
                    public Integer hubId = vhubId;
                })
                .status(HttpStatus.OK).build();
    }

    @PostMapping("/vhub/{vhubId}/devices/{deviceId}")
    public ResponseDTO unlinkIoTHub(@PathVariable Integer vhubId, @PathVariable Integer deviceId) {

        virtualHubMapper.updateDeviceUnlink(deviceId);

        return ResponseDTO.builder()
                .msg("unlink repo")
                .status(HttpStatus.OK)
                .data(new Object(){
                    public Integer devId = deviceId;
                    public Integer hubId = vhubId;
                })
                .build();
    }
}
