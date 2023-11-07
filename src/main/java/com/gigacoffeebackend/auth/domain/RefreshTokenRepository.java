package com.gigacoffeebackend.auth.domain;


import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByToken(final String token);
}
