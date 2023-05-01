package com.sivalabs.techbuzz.users.usecases.verifyemail;

import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailVerificationHandler {
    private final UserRepository userRepository;

    public EmailVerificationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CacheEvict(cacheNames = "user", allEntries = true)
    public void verify(String email, String token) {
        User user = userRepository
                .findByEmailAndVerificationToken(email, token)
                .orElseThrow(() -> new TechBuzzException("Invalid email verification request"));
        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }
}
