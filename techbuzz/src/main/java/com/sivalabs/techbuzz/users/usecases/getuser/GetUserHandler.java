package com.sivalabs.techbuzz.users.usecases.getuser;

import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetUserHandler {
    private final UserRepository userRepository;

    @Cacheable("user")
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
