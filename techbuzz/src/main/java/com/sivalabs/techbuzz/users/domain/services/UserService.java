package com.sivalabs.techbuzz.users.domain.services;

import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import com.sivalabs.techbuzz.users.domain.dtos.CreateUserRequest;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.mappers.UserDTOMapper;
import com.sivalabs.techbuzz.users.domain.models.RoleEnum;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public UserService(
            final PasswordEncoder passwordEncoder,
            final UserRepository userRepository,
            final UserDTOMapper userDTOMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    @Cacheable("user")
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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

    @CacheEvict(cacheNames = "user", allEntries = true)
    public void verifyEmail(String email, String token) {
        User user = userRepository
                .findByEmailAndVerificationToken(email, token)
                .orElseThrow(() -> new TechBuzzException("Invalid email verification request"));
        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.updateVerificationStatus(user);
    }
}
