package com.sejus.sigeplan.application.service;

import com.sejus.sigeplan.application.dto.admin.CreatePermissionRequest;
import com.sejus.sigeplan.application.dto.admin.PermissionResponse;
import com.sejus.sigeplan.application.dto.admin.UpdatePermissionRequest;
import com.sejus.sigeplan.domain.model.Permission;
import com.sejus.sigeplan.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionAdminService {

    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PermissionResponse findById(UUID permissionId) {
        return toResponse(getPermissionOrThrow(permissionId));
    }

    @Transactional
    public PermissionResponse create(CreatePermissionRequest request) {
        String normalizedName = normalizePermissionName(request.name());

        if (permissionRepository.existsByName(normalizedName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Permissão já cadastrada.");
        }

        OffsetDateTime now = OffsetDateTime.now();

        Permission permission = new Permission(
                UUID.randomUUID(),
                normalizedName,
                normalizeDescription(request.description()),
                now,
                now
        );

        return toResponse(permissionRepository.save(permission));
    }

    @Transactional
    public PermissionResponse update(UUID permissionId, UpdatePermissionRequest request) {
        Permission currentPermission = getPermissionOrThrow(permissionId);
        String normalizedName = normalizePermissionName(request.name());

        permissionRepository.findByName(normalizedName)
                .filter(found -> !found.id().equals(permissionId))
                .ifPresent(found -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Permissão já cadastrada.");
                });

        Permission updatedPermission = new Permission(
                currentPermission.id(),
                normalizedName,
                normalizeDescription(request.description()),
                currentPermission.createdAt(),
                OffsetDateTime.now()
        );

        return toResponse(permissionRepository.save(updatedPermission));
    }

    private Permission getPermissionOrThrow(UUID permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permissão não encontrada."));
    }

    private PermissionResponse toResponse(Permission permission) {
        return new PermissionResponse(
                permission.id().toString(),
                permission.name(),
                permission.description()
        );
    }

    private String normalizePermissionName(String name) {
        return name == null ? null : name.trim().toUpperCase();
    }

    private String normalizeDescription(String description) {
        return description == null || description.isBlank() ? null : description.trim();
    }
}