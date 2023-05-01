package com.sivalabs.techbuzz.users.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;

public class User implements Serializable {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email(message = "Invalid email")
    private String email;

    @NotEmpty
    @Size(min = 4)
    private String password;

    private RoleEnum role;

    private boolean verified;

    private String verificationToken;

    public User() {}

    public User(
            final Long id,
            final String name,
            final String email,
            final String password,
            final RoleEnum role,
            final Boolean verified,
            final String verificationToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
        this.verificationToken = verificationToken;
    }

    public boolean isAdminOrModerator() {
        return hasAnyRole(RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_MODERATOR);
    }

    public boolean hasAnyRole(RoleEnum... roles) {
        return Arrays.asList(roles).contains(this.getRole());
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRole(final RoleEnum role) {
        this.role = role;
    }

    public void setVerified(final Boolean verified) {
        this.verified = verified;
    }

    public void setVerificationToken(final String verificationToken) {
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

    public String getPassword() {
        return this.password;
    }

    public RoleEnum getRole() {
        return this.role;
    }

    public Boolean isVerified() {
        return this.verified;
    }

    public String getVerificationToken() {
        return this.verificationToken;
    }
}
