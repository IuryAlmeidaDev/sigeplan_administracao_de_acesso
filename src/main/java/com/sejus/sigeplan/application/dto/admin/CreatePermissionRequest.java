package com.sejus.sigeplan.application.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePermissionRequest(

        @NotBlank(message = "Nome da permissão é obrigatório.")
        @Size(min = 3, max = 120, message = "Nome da permissão deve ter entre 3 e 120 caracteres.")
        String name,

        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres.")
        String description
) {
}