package com.sejus.sigeplan.infrastructure.persistence.repository;

import com.sejus.sigeplan.domain.model.Permission;
import com.sejus.sigeplan.domain.repository.PermissionRepository;
import com.sejus.sigeplan.infrastructure.persistence.entity.PermissionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {

    private final SpringDataPermissionJpaRepository jpaRepository;

    @Override
    public Optional<Permission> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return jpaRepository.findByName(name).map(this::toDomain);
    }

    @Override
    public List<Permission> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public Permission save(Permission permission) {
        PermissionEntity entity = PermissionEntity.builder()
                .id(permission.id())
                .name(permission.name())
                .description(permission.description())
                .createdAt(permission.createdAt())
                .updatedAt(permission.updatedAt())
                .build();

        return toDomain(jpaRepository.save(entity));
    }

    private Permission toDomain(PermissionEntity entity) {
        return new Permission(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}