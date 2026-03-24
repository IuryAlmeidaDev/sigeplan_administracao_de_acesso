package com.sejus.sigeplan.application.dto;

import java.util.Set;

public record AuthenticatedUserResponse(
        String id,
        String fullName,
        String email,
        Set<String> roles
) {
}
