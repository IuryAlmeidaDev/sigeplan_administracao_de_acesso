package com.sejus.sigeplan.application.service;

import com.sejus.sigeplan.application.dto.AuthResponse;
import com.sejus.sigeplan.application.dto.AuthenticatedUserResponse;
import com.sejus.sigeplan.application.dto.LoginRequest;
import com.sejus.sigeplan.application.dto.RegisterUserRequest;
import com.sejus.sigeplan.domain.model.Role;
import com.sejus.sigeplan.domain.model.User;
import com.sejus.sigeplan.domain.repository.UserRepository;
import com.sejus.sigeplan.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterUserRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado.");
        }

        OffsetDateTime now = OffsetDateTime.now();
        User user = new User(
                UUID.randomUUID(),
                request.fullName().trim(),
                normalizedEmail,
                passwordEncoder.encode(request.password()),
                true,
                Set.of(Role.ROLE_USER),
                now,
                now
        );

        User savedUser = userRepository.save(user);
        return buildAuthResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.password())
        );

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas."));

        if (!user.active()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário inativo.");
        }

        return buildAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthenticatedUserResponse me(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        return new AuthenticatedUserResponse(
                user.id().toString(),
                user.fullName(),
                user.email(),
                user.roles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet())
        );
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtService.generateToken(user);
        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationInSeconds(),
                new AuthResponse.UserResponse(
                        user.id().toString(),
                        user.fullName(),
                        user.email(),
                        user.roles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet())
                )
        );
    }
}
