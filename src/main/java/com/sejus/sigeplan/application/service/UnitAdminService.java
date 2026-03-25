package com.sejus.sigeplan.application.service;

import com.sejus.sigeplan.application.dto.admin.CreateUnitRequest;
import com.sejus.sigeplan.application.dto.admin.UnitResponse;
import com.sejus.sigeplan.application.dto.admin.UpdateUnitRequest;
import com.sejus.sigeplan.application.dto.admin.UpdateUnitStatusRequest;
import com.sejus.sigeplan.domain.model.Unit;
import com.sejus.sigeplan.domain.repository.UnitRepository;
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
public class UnitAdminService {

    private final UnitRepository unitRepository;

    @Transactional(readOnly = true)
    public List<UnitResponse> findAll() {
        return unitRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UnitResponse findById(UUID unitId) {
        return toResponse(getUnitOrThrow(unitId));
    }

    @Transactional
    public UnitResponse create(CreateUnitRequest request) {
        String normalizedCode = normalizeCode(request.code());

        if (unitRepository.existsByCode(normalizedCode)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código da unidade já cadastrado.");
        }

        OffsetDateTime now = OffsetDateTime.now();

        Unit unit = new Unit(
                UUID.randomUUID(),
                request.name().trim(),
                normalizedCode,
                normalizeDescription(request.description()),
                true,
                now,
                now
        );

        return toResponse(unitRepository.save(unit));
    }

    @Transactional
    public UnitResponse update(UUID unitId, UpdateUnitRequest request) {
        Unit currentUnit = getUnitOrThrow(unitId);
        String normalizedCode = normalizeCode(request.code());

        unitRepository.findByCode(normalizedCode)
                .filter(found -> !found.id().equals(unitId))
                .ifPresent(found -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Código da unidade já cadastrado.");
                });

        Unit updatedUnit = new Unit(
                currentUnit.id(),
                request.name().trim(),
                normalizedCode,
                normalizeDescription(request.description()),
                currentUnit.active(),
                currentUnit.createdAt(),
                OffsetDateTime.now()
        );

        return toResponse(unitRepository.save(updatedUnit));
    }

    @Transactional
    public UnitResponse updateStatus(UUID unitId, UpdateUnitStatusRequest request) {
        Unit currentUnit = getUnitOrThrow(unitId);

        Unit updatedUnit = new Unit(
                currentUnit.id(),
                currentUnit.name(),
                currentUnit.code(),
                currentUnit.description(),
                request.active(),
                currentUnit.createdAt(),
                OffsetDateTime.now()
        );

        return toResponse(unitRepository.save(updatedUnit));
    }

    private Unit getUnitOrThrow(UUID unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada."));
    }

    private UnitResponse toResponse(Unit unit) {
        return new UnitResponse(
                unit.id().toString(),
                unit.name(),
                unit.code(),
                unit.description(),
                unit.active()
        );
    }

    private String normalizeCode(String code) {
        return code == null ? null : code.trim().toUpperCase();
    }

    private String normalizeDescription(String description) {
        return description == null || description.isBlank() ? null : description.trim();
    }
}