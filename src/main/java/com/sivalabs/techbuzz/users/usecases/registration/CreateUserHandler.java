package com.sivalabs.techbuzz.users.usecases.registration;

import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.users.domain.AuthProvider;
import com.sivalabs.techbuzz.users.domain.RoleEnum;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserRepository;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateUserHandler {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CreateUserHandler(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new ResourceAlreadyExistsException(
                    "User with email " + createUserRequest.email() + " already exists");
        }
        String encPwd = passwordEncoder.encode(createUserRequest.password());
        String verificationToken = UUID.randomUUID().toString();
        User user =
                new User(
                        null,
                        createUserRequest.name(),
                        createUserRequest.email(),
                        encPwd,
                        RoleEnum.ROLE_USER,
                        false,
                        verificationToken,
                        AuthProvider.LOCAL);
        return userRepository.save(user);
    }
}
