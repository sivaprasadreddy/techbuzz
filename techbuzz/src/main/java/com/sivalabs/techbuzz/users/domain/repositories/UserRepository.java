package com.sivalabs.techbuzz.users.domain.repositories;

import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.models.UserProfile;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<UserProfile> findProfileById(Long id);

    boolean existsByEmail(String email);

    List<String> findVerifiedUsersMailIds();

    Optional<User> findByEmailAndVerificationToken(String email, String token);

    User save(User user);

    void updateVerificationStatus(User user);

    void updatePasswordResetToken(User user);

    Optional<User> findByEmailAndPasswordResetToken(String email, String token);

    int updatePassword(String email, String verificationCode, String encodedPassword);
}
