package com.kevin.fincore.user.service;

import com.kevin.fincore.user.dto.CreateUserRequest;
import com.kevin.fincore.user.dto.UserResponse;
import com.kevin.fincore.user.entity.User;
import com.kevin.fincore.user.repository.UserRepository;
import com.kevin.fincore.common.exception.EmailRegisteredException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        String email = normalizeEmail(request.email());

        validateEmailAvailability(email);

        User user = buildUser(request, email);

        User savedUser = userRepository.save(user);

        return UserResponse.fromEntity(savedUser);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private void validateEmailAvailability(String email) {
        if (userRepository.existsByEmail(email)) {
           throw new EmailRegisteredException(
            "Já existe um usuário cadastrado com este e-mail."
            );
        }
    }

    private User buildUser(CreateUserRequest request, String email) {

        User user = new User();

        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );

        return user;
    }
}
