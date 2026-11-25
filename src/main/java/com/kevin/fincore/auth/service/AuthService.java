package com.kevin.fincore.auth.service;

import com.kevin.fincore.auth.dto.LoginRequest; 
import com.kevin.fincore.auth.dto.LoginResponse;
import com.kevin.fincore.common.exception.InvalidCredentialsException;
import com.kevin.fincore.security.JwtService;
import com.kevin.fincore.user.entity.User;
import com.kevin.fincore.user.repository.UserRepository;
import com.kevin.fincore.common.exception.UserNotFoundException;
import com.kevin.fincore.user.dto.UserResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // Indica que a classe é um componente de serviço, responsável por conter a lógica de negócios relacionada à autenticação de usuários.
import org.springframework.transaction.annotation.Transactional; // Permite gerenciar transações de banco de dados, garantindo que as operações sejam executadas de forma atômica e consistente.

import java.util.Locale; // Permite a manipulação de informações relacionadas a idiomas e regiões, como formatação de datas, números e strings, garantindo que o comportamento seja consistente com as convenções locais.
import java.util.UUID;

@Service
public class AuthService { // Responsável por gerenciar a autenticação de usuários, incluindo o processo de login e a geração de tokens JWT.

    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder; 
    private final JwtService jwtService;
    private final long expiration;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            @Value("${security.jwt.expiration}") long expiration // Defini o tempo de expiração do token JWT.
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.expiration = expiration;
    }

    @Transactional(readOnly = true) // Indica que o método é apenas de leitura, evitando alterações no banco de dados durante a execução do login.
    public LoginResponse login(LoginRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches = passwordEncoder.matches( // Verifica se a senha fornecida pelo usuário corresponde à senha armazenada no banco de dados
                request.password(),
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                "Bearer",
                expiration / 1000
        );
    }
        @Transactional(readOnly = true)
        public UserResponse getMyProfile(UUID userId) {

                User user = userRepository.findById(userId)
                        .orElseThrow(UserNotFoundException::new);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
         );
        }
}
