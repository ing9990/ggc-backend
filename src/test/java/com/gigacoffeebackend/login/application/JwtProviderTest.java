package com.gigacoffeebackend.login.application;

import com.gigacoffeebackend.auth.domain.AccessAndRefreshToken;
import com.gigacoffeebackend.login.infra.ExpiredPeriodJwtException;
import com.gigacoffeebackend.login.infra.InvalidJwtException;
import com.gigacoffeebackend.login.infra.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("Claims로 UserId를 가지는 JWT 토큰을 생성한다.")
    @Test
    void create_jwt_token_with_userid() {
        // given // when
        String userId = String.valueOf(9292L);
        AccessAndRefreshToken token = jwtProvider.generateLoginToken(userId);

        // then
        assertThat(token).isNotNull();
        assertThat(token.getAccessToken()).isNotEmpty();
        assertThat(token.getRefreshToken()).isNotEmpty();
    }

    @DisplayName("JWT 토큰에 저장된 UserId는 변하지 않는다.")
    @Test
    void create_jwt_token_and_validate_userid() {
        // given
        String userId = String.valueOf(13L);
        AccessAndRefreshToken token = jwtProvider.generateLoginToken(userId);

        // when
        String subject = jwtProvider.getSubject(token.getAccessToken());
        String refreshSubject = jwtProvider.getSubject(token.getRefreshToken());

        // then
        assertThat(subject).isEqualTo(userId);
        assertThat(refreshSubject).isNull();
    }


    @DisplayName("유효기간이 지난 JWT 토큰은 ExpiredPeriodJwtException를 던진다.")
    @Test
    void throw_jwt_expired_exception_when_token_was_expired() {
        // given
        Long userId = 14L;
        JwtProvider testJwtProvider = new JwtProvider("hello my name is taewoo and my github id is ing9990", 0L, 0L);
        AccessAndRefreshToken accessAndRefreshToken = testJwtProvider.generateLoginToken(String.valueOf(userId));

        // when // then
        assertThatThrownBy(() -> testJwtProvider.validateToken(accessAndRefreshToken.getAccessToken()))
                .isInstanceOf(ExpiredPeriodJwtException.class);
    }

    @DisplayName("JWT를 복호화 할 수 없다면 InvalidJwtException를 던진다.")
    @Test
    void throw_jwt_expired_exception_when_token_cannot_dec() {
        // given
        Long userId = 14L;
        AccessAndRefreshToken accessAndRefreshToken = jwtProvider.generateLoginToken(String.valueOf(userId));
        String token = accessAndRefreshToken.getAccessToken() + " doesn't have any salt?";

        // when // then
        assertThatThrownBy(() -> jwtProvider.validateToken(token))
                .isInstanceOf(InvalidJwtException.class);
    }
}