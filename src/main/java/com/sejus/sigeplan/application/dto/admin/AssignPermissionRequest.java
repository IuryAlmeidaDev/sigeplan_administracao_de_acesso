package com.sejus.sigeplan.application.dto.admin;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignPermissionRequest(
        @NotNull(message = "permissionId é obrigatório.")
        UUID permissionId
) {
}