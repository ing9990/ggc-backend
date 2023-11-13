package com.gigacoffeebackend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.gigacoffeebackend.user.domain.Authority.USER;
import static com.gigacoffeebackend.user.domain.User.user;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class UserTest {

    @DisplayName("소셜ID와 유저 이름으로 닉네임을 파싱한다.")
    @Test
    void parse_with_social_id_and_nickname() {
        // given
        String socialLoginId = "123456789";
        String nickname = "김기가";
        String image = "https://cataas.com/cat";

        // when
        User user = user(socialLoginId, nickname, image);

        // then
        assertThat(user.getDisplayName()).isEqualTo("김기가#6789");
    }

    @DisplayName("유저 권한으로 새로운 유저를 만든다.")
    @Test
    void create_user_with_user_authority() {
        // given
        String socialLoginId = "123456789";
        String nickname = "김기가";
        String image = "https://cataas.com/cat";

        // when
        User user = user(socialLoginId, nickname, image);

        // then
        assertThat(user.getNickName()).isEqualTo(nickname);
        assertThat(user.getSocialLoginId()).isEqualTo(socialLoginId);
        assertThat(user.getAuthority()).isEqualTo(USER);
    }
}