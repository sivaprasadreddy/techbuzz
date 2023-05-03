package com.sivalabs.techbuzz.users.domain.repositories;

import com.sivalabs.techbuzz.users.domain.models.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndVerificationToken(String email, String token);

    User save(User user);

    void updateVerificationStatus(User user);
}
