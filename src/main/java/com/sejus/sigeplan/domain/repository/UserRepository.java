package com.sejus.sigeplan.domain.repository;

import com.sejus.sigeplan.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
}
