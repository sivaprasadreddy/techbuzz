package com.sivalabs.techbuzz.users.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndVerificationToken(String email, String token);

    User save(User user);

    void update(User user);
}
