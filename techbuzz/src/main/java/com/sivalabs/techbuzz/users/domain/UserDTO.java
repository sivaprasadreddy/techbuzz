package com.sivalabs.techbuzz.users.domain;

public class UserDTO {
    private final Long id;
    private final String name;
    private final String email;
    private final RoleEnum role;
    private final boolean verified;
    private final String verificationToken;

    public UserDTO(
            final Long id,
            final String name,
            final String email,
            final RoleEnum role,
            final boolean verified,
            final String verificationToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.verified = verified;
        this.verificationToken = verificationToken;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public RoleEnum getRole() {
        return this.role;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public String getVerificationToken() {
        return this.verificationToken;
    }
}
