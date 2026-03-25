package com.sejus.sigeplan.application.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUnitRequest(

        @NotBlank(message = "Nome da unidade é obrigatório.")
        @Size(min = 3, max = 120, message = "Nome da unidade deve ter entre 3 e 120 caracteres.")
        String name,

        @NotBlank(message = "Código da unidade é obrigatório.")
        @Size(min = 2, max = 30, message = "Código da unidade deve ter entre 2 e 30 caracteres.")
        String code,

        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres.")
        String description
) {
}