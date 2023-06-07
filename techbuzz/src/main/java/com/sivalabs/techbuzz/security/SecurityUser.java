package com.sivalabs.techbuzz.security;

import com.sivalabs.techbuzz.users.domain.models.User;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private final String name;
    private final Long id;

    public SecurityUser(User user) {
        super(
                user.getEmail(),
                user.getPassword(),
                user.isVerified(),
                true,
                true,
                true,
                Set.of(new SimpleGrantedAuthority(user.getRole().name())));

        this.name = user.getName();
        this.id = user.getId();
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
