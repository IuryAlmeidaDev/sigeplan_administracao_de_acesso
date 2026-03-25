package com.sejus.sigeplan.interfaces.rest;

import com.sejus.sigeplan.application.dto.admin.CreatePermissionRequest;
import com.sejus.sigeplan.application.dto.admin.PermissionResponse;
import com.sejus.sigeplan.application.dto.admin.UpdatePermissionRequest;
import com.sejus.sigeplan.application.service.PermissionAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/permissions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PermissionAdminController {

    private final PermissionAdminService permissionAdminService;

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "Listar permissões")
    public List<PermissionResponse> findAll() {
        return permissionAdminService.findAll();
    }

    @GetMapping("/{permissionId}")
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "Buscar permissão por ID")
    public PermissionResponse findById(@PathVariable UUID permissionId) {
        return permissionAdminService.findById(permissionId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "Criar permissão")
    public PermissionResponse create(@Valid @RequestBody CreatePermissionRequest request) {
        return permissionAdminService.create(request);
    }

    @PutMapping("/{permissionId}")
    @PreAuthorize("hasAuthority('PERMISSION_MANAGE')")
    @Operation(summary = "Atualizar permissão")
    public PermissionResponse update(
            @PathVariable UUID permissionId,
            @Valid @RequestBody UpdatePermissionRequest request
    ) {
        return permissionAdminService.update(permissionId, request);
    }
}