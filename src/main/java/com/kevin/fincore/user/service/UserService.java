package com.kevin.fincore.user.service;

import com.kevin.fincore.user.entity.User;
import com.kevin.fincore.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService( UserRepository userRepository, PasswordEncoder passwordEncoder)
        {this.userRepository = userRepository; this.passwordEncoder = passwordEncoder;}

    public User createUser(User user) {

        String normalizedEmail = user.getEmail().trim().toLowerCase(Locale.ROOT);

        // Verifica se o e-mail já está cadastrado
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }

        // Normaliza os dados recebidos
        user.setName(user.getName().trim());
        user.setEmail(normalizedEmail);

        // Criptografa a senha antes de salvar
        user.setPasswordHash(
                passwordEncoder.encode(user.getPasswordHash())
        );

        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {

        String normalizedEmail = email
                .trim()
                .toLowerCase(Locale.ROOT);

        return userRepository.findByEmail(normalizedEmail);
    }
}
