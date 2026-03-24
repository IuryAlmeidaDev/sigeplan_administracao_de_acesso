package com.sejus.sigeplan.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "RegisterUserRequest", description = "Dados para cadastro de usuário")
public record RegisterUserRequest(

        @Schema(description = "Nome completo do usuário", example = "Iury Costa")
        @NotBlank
        @Size(min = 3, max = 120)
        String fullName,

        @Schema(description = "E-mail do usuário", example = "iury@sigeplan.gov.br")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank
        @Size(min = 6, max = 100)
        String password
) {
}