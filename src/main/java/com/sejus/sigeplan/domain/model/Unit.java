package com.sejus.sigeplan.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Unit(
        UUID id,
        String name,
        String code,
        String description,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}