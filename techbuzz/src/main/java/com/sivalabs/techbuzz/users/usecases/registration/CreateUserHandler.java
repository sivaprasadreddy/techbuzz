package com.sivalabs.techbuzz.users.usecases.registration;

import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.users.domain.RoleEnum;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserDTO;
import com.sivalabs.techbuzz.users.domain.UserRepository;
import com.sivalabs.techbuzz.users.mappers.UserDTOMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUserHandler {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public UserDTO createUser(CreateUserRequest createUserRequest) {
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
                        verificationToken);
        User savedUser = userRepository.save(user);
        return userDTOMapper.toDTO(savedUser);
    }
}
