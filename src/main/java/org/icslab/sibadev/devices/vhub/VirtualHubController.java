package org.icslab.sibadev.devices.vhub;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VirtualHubController {

    @Autowired
    private VirtualHubMapper virtualHubMapper;

    @PostMapping("/vhub")
    public ResponseDTO virtualHubCreate(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();

        virtualHubMapper.createHub(userId);

        return ResponseDTO.builder()
                .msg("vhub creation")
                .status(HttpStatus.OK).build();
    }
}
