package com.sejus.sigeplan.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginRequest", description = "Dados para autenticação do usuário")
public record LoginRequest(

        @Schema(description = "E-mail do usuário", example = "admin@sigeplan.gov.br")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank
        String password
) {
}