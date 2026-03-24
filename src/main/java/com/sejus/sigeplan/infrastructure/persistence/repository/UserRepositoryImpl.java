package com.sejus.sigeplan.infrastructure.persistence.repository;

import com.sejus.sigeplan.domain.model.User;
import com.sejus.sigeplan.domain.repository.UserRepository;
import com.sejus.sigeplan.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserJpaRepository jpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.builder()
                .id(user.id())
                .fullName(user.fullName())
                .email(user.email())
                .passwordHash(user.passwordHash())
                .active(user.active())
                .roles(user.roles())
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
                entity.getPasswordHash(),
                entity.isActive(),
                entity.getRoles(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
