package com.gigacoffeebackend.global.aop;

import com.gigacoffeebackend.login.infra.InvalidJwtException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BearerAuthorizationExtractorTest {

    @Autowired
    private BearerAuthorizationExtractor bearerAuthorizationExtractor;


    @DisplayName("Bearer 토큰을 토큰만 분리한다.")
    @Test
    void parse_bearer_token_header() {
        // given
        String token = "Bearer hello my name is taewoo and my github id is ing9990";

        // when
        String result = bearerAuthorizationExtractor.extractAccessToken(token);

        // then
        Assertions.assertThat(result).isEqualTo("hello my name is taewoo and my github id is ing9990");
    }


    @DisplayName("Bearer 토큰을 파싱할 수 없으면 InvalidJwtException을 던진다.")
    @Test
    void throw_exception_when_token_is_invalid() {
        // given
        String token = "111er hello my name is taewoo and my github id is ing9990";

        // when // then
        Assertions.assertThatThrownBy(() -> bearerAuthorizationExtractor.extractAccessToken(token))
                .isInstanceOf(InvalidJwtException.class);
    }


}