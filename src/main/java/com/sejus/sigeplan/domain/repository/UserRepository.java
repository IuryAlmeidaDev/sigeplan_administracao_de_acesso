package com.sejus.sigeplan.domain.repository;

import com.sejus.sigeplan.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    User save(User user);
}