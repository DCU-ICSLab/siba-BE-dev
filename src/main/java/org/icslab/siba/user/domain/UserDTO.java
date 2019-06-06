package org.icslab.siba.user.domain;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class UserDTO {
    private Long userId;

    private String providerId;

    private String name;

    private String email;

    private String profileImage;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
