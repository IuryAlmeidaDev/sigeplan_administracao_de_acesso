package com.sejus.sigeplan.interfaces.rest;

import com.sejus.sigeplan.application.dto.admin.CreateUnitRequest;
import com.sejus.sigeplan.application.dto.admin.UnitResponse;
import com.sejus.sigeplan.application.dto.admin.UpdateUnitRequest;
import com.sejus.sigeplan.application.dto.admin.UpdateUnitStatusRequest;
import com.sejus.sigeplan.application.service.UnitAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/units")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UnitAdminController {

    private final UnitAdminService unitAdminService;

    @GetMapping
    @PreAuthorize("hasAuthority('UNIT_LINK')")
    @Operation(summary = "Listar unidades")
    public List<UnitResponse> findAll() {
        return unitAdminService.findAll();
    }

    @GetMapping("/{unitId}")
    @PreAuthorize("hasAuthority('UNIT_LINK')")
    @Operation(summary = "Buscar unidade por ID")
    public UnitResponse findById(@PathVariable UUID unitId) {
        return unitAdminService.findById(unitId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('UNIT_LINK')")
    @Operation(summary = "Criar unidade")
    public UnitResponse create(@Valid @RequestBody CreateUnitRequest request) {
        return unitAdminService.create(request);
    }

    @PutMapping("/{unitId}")
    @PreAuthorize("hasAuthority('UNIT_LINK')")
    @Operation(summary = "Atualizar unidade")
    public UnitResponse update(
            @PathVariable UUID unitId,
            @Valid @RequestBody UpdateUnitRequest request
    ) {
        return unitAdminService.update(unitId, request);
    }

    @PatchMapping("/{unitId}/status")
    @PreAuthorize("hasAuthority('UNIT_LINK')")
    @Operation(summary = "Ativar ou desativar unidade")
    public UnitResponse updateStatus(
            @PathVariable UUID unitId,
            @Valid @RequestBody UpdateUnitStatusRequest request
    ) {
        return unitAdminService.updateStatus(unitId, request);
    }
}