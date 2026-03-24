package com.sejus.sigeplan.application.dto;

import java.util.Set;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UserResponse user
) {
    public record UserResponse(
            String id,
            String fullName,
            String email,
            Set<String> roles
    ) {
    }
}
