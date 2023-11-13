package com.gigacoffeebackend.auth.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @DisplayName("Refresh 토큰을 저장한다.")
    @Test
    void save_refresh_token() {
        // given
        String token = "eythisisjwttoken";
        Long userId = 1L;
        RefreshToken refreshToken = new RefreshToken(token, userId);

        // when
        refreshTokenRepository.save(refreshToken);

        // then
        Assertions.assertThat(refreshTokenRepository.findAll()).hasSize(1);
    }

    @DisplayName("Refresh 토큰을 가져온다.")
    @Test
    void get_refresh_token_and_expect() {
        String token = "eythisisjwttoken";
        Long userId = 1L;
        RefreshToken refreshToken = new RefreshToken(token, userId);

        // when
        refreshTokenRepository.save(refreshToken);

        // then
        Assertions.assertThat(refreshTokenRepository.findAll()).hasSize(1);
        Assertions.assertThat(refreshTokenRepository.findByToken(token)).isPresent();
        Assertions.assertThat(refreshTokenRepository.findByToken(token).get().getToken()).isEqualTo(token);
    }


}