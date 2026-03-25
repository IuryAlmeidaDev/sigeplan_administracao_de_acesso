package com.sejus.sigeplan.application.dto.admin;

public record UnitResponse(
        String id,
        String name,
        String code,
        String description,
        boolean active
) {
}