package com.sejus.sigeplan.infrastructure.persistence.repository;

import com.sejus.sigeplan.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataPermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {

    Optional<PermissionEntity> findByName(String name);

    boolean existsByName(String name);
}