package com.sivalabs.techbuzz.users.domain.services;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import com.sivalabs.techbuzz.notifications.EmailService;
import com.sivalabs.techbuzz.users.domain.dtos.CreateUserRequest;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.mappers.UserDTOMapper;
import com.sivalabs.techbuzz.users.domain.models.RoleEnum;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.models.UserProfile;
import com.sivalabs.techbuzz.users.domain.repositories.UserRepository;
import java.util.List;
import java.util.Map;
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
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public UserService(
            EmailService emailService,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            UserDTOMapper userDTOMapper) {
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    @Cacheable("user")
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserProfile> getUserProfile(Long id) {
        return userRepository.findProfileById(id);
    }

    public List<String> findVerifiedUsersMailIds() {
        return userRepository.findVerifiedUsersMailIds();
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

    public Optional<UserDTO> getUserDTO(String emailID) {
        return this.getUserByEmail(emailID).map(userDTOMapper::toDTO);
    }

    public void sendVerificationEmail(String baseUrl, UserDTO userDTO) {
        String params =
                "email=" + encode(userDTO.email(), UTF_8) + "&token=" + encode(userDTO.verificationToken(), UTF_8);
        String verificationUrl = baseUrl + "/verify-email?" + params;
        String to = userDTO.email();
        String subject = "TechBuzz - Email verification";
        Map<String, Object> paramsMap = Map.of("username", userDTO.name(), "verificationUrl", verificationUrl);
        emailService.sendEmail("email/verify-email", paramsMap, to, subject);
    }
}
