package com.kevin.fincore.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret; // Representa uma fonte de chave secreta imutável usada para assinar e verificar tokens JWT.
import com.nimbusds.jose.proc.SecurityContext; // Representa o contexto de segurança usado durante o processamento de tokens JWT, fornecendo informações sobre a chave e o algoritmo de assinatura.

import org.springframework.beans.factory.annotation.Value; // Permite injetar valores de propriedades externas, como o segredo JWT, no código da aplicação.
import org.springframework.context.annotation.Bean; // Indica que o método produz um bean gerenciado pelo Spring, permitindo que ele seja injetado em outras partes da aplicação.
import org.springframework.context.annotation.Configuration; // Indica que a classe contém definições de configuração e beans do Spring, permitindo que o Spring os descubra e os registre no contexto da aplicação.

import org.springframework.security.oauth2.jose.jws.MacAlgorithm; // Representa o algoritmo de assinatura HMAC usado para assinar e verificar tokens JWT.
import org.springframework.security.oauth2.jwt.JwtDecoder; // Fornece a funcionalidade de decodificação de tokens JWT, permitindo que a aplicação verifique a assinatura e extraia as informações contidas no token.
import org.springframework.security.oauth2.jwt.JwtEncoder; // Fornece a funcionalidade de codificação de tokens JWT, permitindo que a aplicação crie tokens assinados com informações específicas.  
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder; // Implementação do JwtDecoder que utiliza a biblioteca Nimbus JOSE + JWT para decodificar tokens JWT.
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder; // Implementação do JwtEncoder que utiliza a biblioteca Nimbus JOSE + JWT para codificar tokens JWT.

import javax.crypto.SecretKey; // Representa uma chave secreta usada para assinar e verificar tokens JWT, garantindo a integridade e autenticidade dos tokens.
import javax.crypto.spec.SecretKeySpec; // Fornece uma implementação concreta da interface SecretKey, permitindo criar uma chave secreta a partir de um array de bytes e especificar o algoritmo de assinatura a ser usado.
import java.util.Base64; // Fornece métodos para codificar e decodificar dados em Base64, permitindo que o segredo JWT seja armazenado de forma segura e convertido em bytes para uso na criação da chave secreta.

// Classe responsável por configurar os beans relacionados à geração e validação de tokens JWT na aplicação.
@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret}") // Injeta o valor do segredo JWT definido nas propriedades da aplicação, permitindo que ele seja usado para criar a chave secreta.
    private String jwtSecret; // Armazena o segredo JWT usado para assinar e verificar tokens JWT, garantindo a segurança da aplicação.

    @Bean // Indica que o método produz um bean gerenciado pelo Spring, permitindo que ele seja injetado em outras partes da aplicação.
    public SecretKey secretKey() { //
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret); // Decodifica o segredo JWT em bytes usando Base64, permitindo que ele seja usado para criar a chave secreta.

        return new SecretKeySpec( // Cria uma instância de SecretKeySpec usando os bytes do segredo e o algoritmo HmacSHA256, permitindo que a chave seja usada para assinar e verificar tokens JWT.
                keyBytes,
                "HmacSHA256"
        );
    }
    
    @Bean 
    public JwtEncoder jwtEncoder(SecretKey secretKey) { // Cria um bean JwtEncoder usando a chave secreta fornecida, permitindo que a aplicação gere tokens JWT assinados com o segredo configurado.
        return new NimbusJwtEncoder(
                new ImmutableSecret<SecurityContext>(secretKey) // Cria uma instância de ImmutableSecret usando a chave secreta, permitindo que o JwtEncoder use essa chave para assinar os tokens JWT.
        );
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) { 
        return NimbusJwtDecoder // Cria um bean JwtDecoder usando a chave secreta fornecida, permitindo que a aplicação decodifique e verifique tokens JWT assinados com o segredo configurado.
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}