package com.sejus.sigeplan.application.dto.admin;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignUnitRequest(
        @NotNull(message = "unitId é obrigatório.")
        UUID unitId
) {
}