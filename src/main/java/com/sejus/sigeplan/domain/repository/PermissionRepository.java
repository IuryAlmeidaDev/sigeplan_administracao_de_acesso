package com.sejus.sigeplan.domain.repository;

import com.sejus.sigeplan.domain.model.Permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {

    Optional<Permission> findById(UUID id);

    Optional<Permission> findByName(String name);

    List<Permission> findAll();

    boolean existsByName(String name);

    Permission save(Permission permission);
}