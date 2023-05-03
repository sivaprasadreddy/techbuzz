package com.sivalabs.techbuzz.users.usecases.getuser;

import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.repositories.UserRepository;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetUserHandler {
    private final UserRepository userRepository;

    public GetUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("user")
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
