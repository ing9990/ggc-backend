package com.gigacoffeebackend.auth.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    boolean existsByToken(final String token);

    @Query("select r from RefreshToken r where r.token = ?1")
    Optional<RefreshToken> findByToken(String refreshTokenRequest);

    @Transactional
    @Modifying
    @Query("delete from RefreshToken r where r.userId = ?1")
    void deleteByUserId(Long id);
}
