package com.sejus.sigeplan.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Permission(
        UUID id,
        String name,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}