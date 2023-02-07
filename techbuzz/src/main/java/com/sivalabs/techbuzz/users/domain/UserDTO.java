package com.sivalabs.techbuzz.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private RoleEnum role;
    private boolean verified;
    private String verificationToken;
}
