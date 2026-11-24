package com.kevin.fincore.config;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwtTestController {

    @GetMapping("/api/test/secure")
    public Map<String, String> secure(Authentication authentication) {

        return Map.of(
                "message", "Acesso autorizado!",
                "userId", authentication.getName()
        );
    }
}
