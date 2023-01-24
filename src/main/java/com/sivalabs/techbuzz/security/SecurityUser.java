package com.sivalabs.techbuzz.security;

import com.sivalabs.techbuzz.users.domain.User;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private final String name;

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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SecurityUser that = (SecurityUser) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }
}
