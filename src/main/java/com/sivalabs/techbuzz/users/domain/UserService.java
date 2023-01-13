package com.sivalabs.techbuzz.users.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean isUserExistsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Transactional
	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email " + user.getEmail() + " is already in use");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

}
