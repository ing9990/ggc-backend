package com.gigacoffeebackend.auth.application.oauth;

import com.gigacoffeebackend.auth.application.AuthException;
import com.gigacoffeebackend.auth.application.OAuthProviderMapper;
import com.gigacoffeebackend.auth.application.providers.GoogleProviderAdapter;
import com.gigacoffeebackend.auth.application.providers.KakaoProviderAdapter;
import com.gigacoffeebackend.auth.application.providers.OAuthProviderPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OAuthProviderMapperTest {

    @Autowired
    private OAuthProviderMapper oAuthProviderMapper;

    @DisplayName("각 소셜 로그인 타입에 해당하는 Mapper로 매핑된다. - 구글")
    @Test
    void mapping_each_social_type_google() {
        // given
        String socialName = "google";

        // when
        OAuthProviderPort oAuthProviderPort = oAuthProviderMapper.mapping(socialName);

        // then
        assertThat(oAuthProviderPort)
                .isInstanceOf(GoogleProviderAdapter.class);
    }

    @DisplayName("각 소셜 로그인 타입에 해당하는 Mapper로 매핑된다. - 카카오")
    @Test
    void mapping_each_social_type_kakao() {
        // given
        String socialName = "kakao";

        // when
        OAuthProviderPort oAuthProviderPort = oAuthProviderMapper.mapping(socialName);

        // then
        assertThat(oAuthProviderPort)
                .isInstanceOf(KakaoProviderAdapter.class);
    }

    @DisplayName("지원하지 않는 소셜 로그인으로 매핑되면 AuthException을 던진다.")
    @Test
    void throw_exception_when_map_to_other_login() {
        // given
        String socialName = "twitter";

        // when // then
        Assertions.assertThatThrownBy(() -> oAuthProviderMapper.mapping(socialName))
                .isInstanceOf(AuthException.class);
    }
}