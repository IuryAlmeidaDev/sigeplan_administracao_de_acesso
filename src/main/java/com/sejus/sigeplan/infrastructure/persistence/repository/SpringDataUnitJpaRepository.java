package com.sejus.sigeplan.infrastructure.persistence.repository;

import com.sejus.sigeplan.infrastructure.persistence.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUnitJpaRepository extends JpaRepository<UnitEntity, UUID> {

    Optional<UnitEntity> findByCode(String code);

    boolean existsByCode(String code);
}