package org.icslab.sibadev.user;

import lombok.extern.slf4j.Slf4j;
import org.icslab.sibadev.clog.domain.CLogVO;
import org.icslab.sibadev.common.config.security.oauth2.UserPrincipal;
import org.icslab.sibadev.common.domain.response.ResponseDTO;
import org.icslab.sibadev.devices.device.domain.DeviceDTO;
import org.icslab.sibadev.devices.device.domain.DeviceShortDTO;
import org.icslab.sibadev.devices.vhub.domain.VirtualHubDTO;
import org.icslab.sibadev.devices.vhub.services.DeviceGroupingService;
import org.icslab.sibadev.mappers.CLogMapper;
import org.icslab.sibadev.mappers.DeviceMapper;
import org.icslab.sibadev.mappers.UserMapper;
import org.icslab.sibadev.mappers.VirtualHubMapper;
import org.icslab.sibadev.user.domain.UserDTO;
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
    private VirtualHubMapper virtualHubMapper;

    @Autowired
    private CLogMapper cLogMapper;

    @Autowired
    private DeviceGroupingService deviceGroupingService;

    @GetMapping(value = "/user")
    public ResponseDTO kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        UserDTO userInfo = userMapper.getUser(userId).get();

        List<DeviceShortDTO> devices = deviceMapper.getDeviceAndHub(userId);

        List<VirtualHubDTO> hubInfoList = deviceGroupingService.grouping(devices);

        List<DeviceDTO> deviceInfo = deviceMapper.getDevices(userId);

        List<CLogVO> clogs = cLogMapper.selectCLogs(userId);

        return ResponseDTO.builder()
            .msg("user information")
            .status(HttpStatus.OK)
            .data(new Object(){
                public UserDTO user = userInfo;
                public List<VirtualHubDTO> hubInfo = hubInfoList;
                public List<DeviceDTO> deviceList = deviceInfo;
                public List<CLogVO> clogList = clogs;
            }).build();
    }
}
