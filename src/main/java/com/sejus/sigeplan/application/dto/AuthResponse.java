package com.sejus.sigeplan.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(name = "AuthResponse", description = "Resposta de autenticação")
public record AuthResponse(

        @Schema(description = "Token JWT de acesso", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,

        @Schema(description = "Tipo do token", example = "Bearer")
        String tokenType,

        @Schema(description = "Tempo de expiração do token em segundos", example = "86400")
        long expiresIn,

        UserResponse user
) {
    @Schema(name = "AuthUserResponse", description = "Dados resumidos do usuário autenticado")
    public record UserResponse(

            @Schema(description = "ID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
            String id,

            @Schema(description = "Nome completo", example = "Iury Costa")
            String fullName,

            @Schema(description = "E-mail", example = "iury@sigeplan.gov.br")
            String email,

            @Schema(description = "Perfis de acesso do usuário")
            Set<String> roles
    ) {
    }
}