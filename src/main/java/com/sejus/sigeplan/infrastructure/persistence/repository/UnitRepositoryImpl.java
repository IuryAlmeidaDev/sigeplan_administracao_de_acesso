package com.sejus.sigeplan.infrastructure.persistence.repository;

import com.sejus.sigeplan.domain.model.Unit;
import com.sejus.sigeplan.domain.repository.UnitRepository;
import com.sejus.sigeplan.infrastructure.persistence.entity.UnitEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UnitRepositoryImpl implements UnitRepository {

    private final SpringDataUnitJpaRepository jpaRepository;

    @Override
    public Optional<Unit> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Unit> findByCode(String code) {
        return jpaRepository.findByCode(code).map(this::toDomain);
    }

    @Override
    public List<Unit> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    @Override
    public Unit save(Unit unit) {
        UnitEntity entity = UnitEntity.builder()
                .id(unit.id())
                .name(unit.name())
                .code(unit.code())
                .description(unit.description())
                .active(unit.active())
                .createdAt(unit.createdAt())
                .updatedAt(unit.updatedAt())
                .build();

        return toDomain(jpaRepository.save(entity));
    }

    private Unit toDomain(UnitEntity entity) {
        return new Unit(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getDescription(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}