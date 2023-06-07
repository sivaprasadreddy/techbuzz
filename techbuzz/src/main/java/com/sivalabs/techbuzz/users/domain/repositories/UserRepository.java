package com.sivalabs.techbuzz.users.domain.repositories;

import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.models.UserProfile;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    UserProfile findProfileById(Long id);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndVerificationToken(String email, String token);

    User save(User user);

    void updateVerificationStatus(User user);
}
