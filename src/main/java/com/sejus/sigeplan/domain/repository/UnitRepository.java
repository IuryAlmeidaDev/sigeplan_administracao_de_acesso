package com.sejus.sigeplan.domain.repository;

import com.sejus.sigeplan.domain.model.Unit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnitRepository {

    Optional<Unit> findById(UUID id);

    Optional<Unit> findByCode(String code);

    List<Unit> findAll();

    boolean existsByCode(String code);

    Unit save(Unit unit);
}