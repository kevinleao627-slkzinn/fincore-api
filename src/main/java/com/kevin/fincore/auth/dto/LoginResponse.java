package com.kevin.fincore.auth.dto;

public record LoginResponse(
    String acessToken,
    String tokenType,
    long expiresIn
) {}