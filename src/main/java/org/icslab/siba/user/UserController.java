package org.icslab.siba.user;

import lombok.extern.slf4j.Slf4j;
import org.icslab.siba.common.config.security.oauth2.UserPrincipal;
import org.icslab.siba.common.domain.response.ResponseDTO;
import org.icslab.siba.user.domain.UserDTO;
import org.icslab.siba.mappers.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    private UserMapper userMapper;

    @GetMapping(value = "/user")
    public ResponseDTO kakaoAuthoriaztion(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserDTO userInfo = userMapper.getUser(userPrincipal.getId()).get();


        return ResponseDTO.builder()
                .msg("userInfoDto information")
                .status(HttpStatus.OK)
                .data(new Object(){
                    public UserDTO user = userInfo;
                }).build();
    }
}
