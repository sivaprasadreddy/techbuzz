package com.sivalabs.techbuzz.users.domain.models;

import java.time.LocalDateTime;

public class UserProfile {

    private Long id;
    private String name;
    private LocalDateTime activeFrom;

    private String email;

    public UserProfile(Long id, String name, String email, LocalDateTime activeFrom) {
        this.id = id;
        this.name = name;
        this.activeFrom = activeFrom;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDateTime activeFrom) {
        this.activeFrom = activeFrom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
