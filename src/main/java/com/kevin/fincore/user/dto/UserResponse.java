package com.kevin.fincore.user.dto;

import com.kevin.fincore.user.entity.User;

import java.util.UUID;
import java.time.LocalDateTime;

public record UserResponse(
    UUID id,
    String name,
    String email,
    LocalDateTime createdAt
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }
}
