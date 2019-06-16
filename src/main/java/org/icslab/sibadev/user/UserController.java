package org.icslab.sibadev.user;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubDTO;
import org.icslab.sibadev.devices.vhub.services.DeviceGroupingService;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.user.domain.UserDTO;
import org.icslab.sibadev.mappers.UserMapper;
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

        List<VirtualHubDTO> hubInfoList = deviceGroupingService.grouping(deviceMapper.getDevices(userId));

        System.out.println(userId);

        return ResponseDTO.builder()
            .msg("user information")
            .status(HttpStatus.OK)
            .data(new Object(){
                public UserDTO user = userInfo;
                public List<VirtualHubDTO> hubInfo = hubInfoList;
            }).build();
    }
}
