package com.sejus.sigeplan.domain.model;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record User(
        UUID id,
        String fullName,
        String email,
        String passwordHash,
        boolean active,
        Set<Role> roles,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
