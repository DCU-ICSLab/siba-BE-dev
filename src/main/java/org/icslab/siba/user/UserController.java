package org.icslab.siba.user;

import lombok.extern.slf4j.Slf4j;
import org.icslab.siba.common.config.security.oauth2.UserPrincipal;
import org.icslab.siba.common.domain.response.ResponseDTO;
import org.icslab.siba.devices.vhub.domain.VirtualHubDTO;
import org.icslab.siba.devices.vhub.services.DeviceGroupingService;
import org.icslab.siba.mappers.DeviceMapper;
import org.icslab.siba.user.domain.UserDTO;
import org.icslab.siba.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceGroupingService deviceGroupingService;

    @GetMapping(value = "/user")
    public ResponseDTO kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        UserDTO userInfo = userMapper.getUser(userId).get();

        return ResponseDTO.builder()
            .msg("user information")
            .status(HttpStatus.OK)
            .data(new Object(){
                public UserDTO user = userInfo;
                public List<VirtualHubDTO> hubInfo = deviceGroupingService.grouping(deviceMapper.getDevices(userId));;
            }).build();
    }
}
