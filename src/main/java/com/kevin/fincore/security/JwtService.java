package com.kevin.fincore.security;

import com.kevin.fincore.user.entity.User; // Representa a entidade de usuário que contém informações sobre o usuário, como nome, email, senha, entre outros.

import org.springframework.beans.factory.annotation.Value; // Permite injetar valores de propriedades externas, como o segredo JWT, no código da aplicação.
import org.springframework.security.oauth2.jose.jws.MacAlgorithm; // Representa o algoritmo de assinatura HMAC usado para assinar e verificar tokens JWT.
import org.springframework.security.oauth2.jwt.JwtClaimsSet; // Representa o conjunto de reivindicações (claims) contidas em um token JWT, permitindo que a aplicação defina e acesse informações específicas do token.
import org.springframework.security.oauth2.jwt.JwtEncoder; // Fornece a funcionalidade de codificação de tokens JWT, permitindo que a aplicação crie tokens assinados com informações específicas.
import org.springframework.security.oauth2.jwt.JwtEncoderParameters; // Representa os parâmetros necessários para codificar um token JWT, incluindo o conjunto de reivindicações (claims) e o cabeçalho do token.
import org.springframework.security.oauth2.jwt.JwsHeader; // Representa o cabeçalho de um token JWT, que contém informações sobre o algoritmo de assinatura e outros metadados do token.

import org.springframework.stereotype.Service; // Indica que a classe é um serviço do Spring, permitindo que ela seja gerenciada pelo contêiner de injeção de dependência do Spring e possa ser injetada em outras partes da aplicação.

import java.time.Instant; // Representa um ponto específico no tempo, permitindo que a aplicação defina e acesse informações de data e hora em tokens JWT.

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long expiration;
    
    public JwtService(JwtEncoder jwtEncoder, @Value("${security.jwt.expiration}") long expiration) { // Construtor que injeta o JwtEncoder e o tempo de expiração do token JWT, permitindo que a aplicação gere tokens com validade definida.
        this.jwtEncoder = jwtEncoder;
        this.expiration = expiration;
    }

     public String generateToken(User user) { // Gera um token JWT para o usuário fornecido, incluindo informações como o ID do usuário, email e tempo de expiração do token.

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder() // Cria um conjunto de reivindicações (claims) para o token JWT, incluindo informações como o emissor, tempo de emissão, tempo de expiração, ID do usuário e email.
                .issuer("fincore-api")
                .issuedAt(now)
                .expiresAt(now.plusMillis(expiration))
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .build();

        JwsHeader header = JwsHeader //.builder() // Cria o cabeçalho do token JWT, especificando o algoritmo de assinatura HMAC SHA-256 (HS256) a ser usado para assinar o token.
                .with(MacAlgorithm.HS256)
                .build();

        return jwtEncoder.encode( // Codifica o token JWT usando o JwtEncoder, passando o cabeçalho e o conjunto de reivindicações (claims) como parâmetros, e retorna o valor do token gerado.
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }
}