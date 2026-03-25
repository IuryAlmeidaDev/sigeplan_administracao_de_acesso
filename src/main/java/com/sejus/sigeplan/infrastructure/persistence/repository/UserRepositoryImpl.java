package com.sejus.sigeplan.infrastructure.persistence.repository;

import com.sejus.sigeplan.domain.model.Permission;
import com.sejus.sigeplan.domain.model.Role;
import com.sejus.sigeplan.domain.model.Unit;
import com.sejus.sigeplan.domain.model.User;
import com.sejus.sigeplan.domain.repository.UserRepository;
import com.sejus.sigeplan.infrastructure.persistence.entity.PermissionEntity;
import com.sejus.sigeplan.infrastructure.persistence.entity.RoleEntity;
import com.sejus.sigeplan.infrastructure.persistence.entity.UnitEntity;
import com.sejus.sigeplan.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserJpaRepository jpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return jpaRepository.findByCpf(cpf).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.builder()
                .id(user.id())
                .fullName(user.fullName())
                .email(user.email())
                .cpf(user.cpf())
                .passwordHash(user.passwordHash())
                .active(user.active())
                .roles(toRoleEntities(user.roles()))
                .units(toUnitEntities(user.units()))
                .createdAt(user.createdAt())
                .updatedAt(user.updatedAt())
                .build();

        return toDomain(jpaRepository.save(entity));
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getPasswordHash(),
                entity.isActive(),
                toRoles(entity.getRoles()),
                toUnits(entity.getUnits()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private Set<Role> toRoles(Set<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new Role(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        toPermissions(role.getPermissions()),
                        role.getCreatedAt(),
                        role.getUpdatedAt()
                ))
                .collect(Collectors.toSet());
    }

    private Set<Permission> toPermissions(Set<PermissionEntity> permissions) {
        return permissions.stream()
                .map(permission -> new Permission(
                        permission.getId(),
                        permission.getName(),
                        permission.getDescription(),
                        permission.getCreatedAt(),
                        permission.getUpdatedAt()
                ))
                .collect(Collectors.toSet());
    }

    private Set<Unit> toUnits(Set<UnitEntity> units) {
        return units.stream()
                .map(unit -> new Unit(
                        unit.getId(),
                        unit.getName(),
                        unit.getCode(),
                        unit.getDescription(),
                        unit.isActive(),
                        unit.getCreatedAt(),
                        unit.getUpdatedAt()
                ))
                .collect(Collectors.toSet());
    }

    private Set<RoleEntity> toRoleEntities(Set<Role> roles) {
        return roles.stream()
                .map(role -> RoleEntity.builder()
                        .id(role.id())
                        .name(role.name())
                        .description(role.description())
                        .permissions(
                                role.permissions().stream()
                                        .map(permission -> PermissionEntity.builder()
                                                .id(permission.id())
                                                .name(permission.name())
                                                .description(permission.description())
                                                .createdAt(permission.createdAt())
                                                .updatedAt(permission.updatedAt())
                                                .build())
                                        .collect(Collectors.toSet())
                        )
                        .createdAt(role.createdAt())
                        .updatedAt(role.updatedAt())
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<UnitEntity> toUnitEntities(Set<Unit> units) {
        return units.stream()
                .map(unit -> UnitEntity.builder()
                        .id(unit.id())
                        .name(unit.name())
                        .code(unit.code())
                        .description(unit.description())
                        .active(unit.active())
                        .createdAt(unit.createdAt())
                        .updatedAt(unit.updatedAt())
                        .build())
                .collect(Collectors.toSet());
    }
}