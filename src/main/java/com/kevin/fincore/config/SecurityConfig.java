package com.kevin.fincore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Permite configurar a segurança da aplicação, definindo regras de autenticação, autorização e proteção contra ataques, como CSRF (Cross-Site Request Forgery).
import org.springframework.security.config.http.SessionCreationPolicy; // Define a política de criação de sessão para a aplicação, especificando que a aplicação não deve criar ou gerenciar sessões de usuário, pois utiliza tokens JWT para autenticação.
import org.springframework.security.web.SecurityFilterChain; // Define a cadeia de filtros de segurança para a aplicação, permitindo que o Spring Security processe requisições HTTP e aplique regras de autenticação e autorização.

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) // Configura a cadeia de filtros de segurança para a aplicação, definindo regras de autenticação, autorização e gerenciamento de sessões.
            throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desativa a proteção CSRF (Cross-Site Request Forgery), pois a aplicação utiliza tokens JWT para autenticação, tornando a proteção CSRF desnecessária.

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth // Configura as regras de autorização para diferentes endpoints da aplicação, permitindo ou restringindo o acesso com base na autenticação do usuário.

                        .requestMatchers( // Define os endpoints que podem ser acessados sem autenticação, permitindo que usuários não autenticados realizem ações específicas, como criar contas ou fazer login.
                                HttpMethod.POST, 
                                "/api/users",
                                "/api/auth/login"
                        ).permitAll()

                        .requestMatchers( // Define o endpoint de verificação de saúde da aplicação, permitindo que seja acessado sem autenticação, facilitando o monitoramento do estado da aplicação.
                                "/actuator/health"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> /// Configura a aplicação como um servidor de recursos OAuth2, permitindo que ela valide tokens JWT para autenticação e autorização de usuários.
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter()
                                )
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() { // Cria um conversor de autenticação JWT personalizado, permitindo que a aplicação extraia as autoridades (roles) do token JWT e as utilize para autorização de acesso aos endpoints.

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return converter;
    }
}


