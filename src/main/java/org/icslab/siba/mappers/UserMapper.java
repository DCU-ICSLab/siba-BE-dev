package org.icslab.siba.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.icslab.siba.user.domain.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {

    Optional<UserDTO> getUser(Long id);

    Optional<UserDTO> getUserByProviderId(String providerId);

    UserDTO getUserByUserId(Long userId);

    UserDTO getUserByEmail(String email);

    void save(UserDTO user);

    void update(UserDTO user);
}
