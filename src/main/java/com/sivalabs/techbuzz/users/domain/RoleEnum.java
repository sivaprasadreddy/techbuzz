package com.sivalabs.techbuzz.users.domain;

import java.util.List;
import java.util.stream.Collectors;

public enum RoleEnum {
    ROLE_ADMIN,
    ROLE_MODERATOR,
    ROLE_USER;

    public static String getRoleHierarchy() {
        List<RoleEnum> hierarchy = List.of(ROLE_ADMIN, ROLE_MODERATOR, ROLE_USER);
        return hierarchy.stream().map(RoleEnum::name).collect(Collectors.joining(" > "));
    }
}
