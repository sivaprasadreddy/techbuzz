package com.sivalabs.techbuzz.users.usecases.registration;

import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.models.RoleEnum;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.repositories.UserRepository;
import com.sivalabs.techbuzz.users.mappers.UserDTOMapper;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateUserHandler {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public CreateUserHandler(
            final PasswordEncoder passwordEncoder,
            final UserRepository userRepository,
            final UserDTOMapper userDTOMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    @CacheEvict(cacheNames = "user", allEntries = true)
    public UserDTO createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new ResourceAlreadyExistsException(
                    "User with email " + createUserRequest.email() + " already exists");
        }
        String encPwd = passwordEncoder.encode(createUserRequest.password());
        String verificationToken = UUID.randomUUID().toString();
        User user = new User(
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
