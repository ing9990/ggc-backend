package com.gigacoffeebackend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("소셜ID와 유저 이름으로 닉네임을 파싱한다.")
    @Test
    void 소셜ID와_유저이름으로_닉네임을_파싱한다() {
        // given
        String socialLoginId = "123456789";
        String username = "김기가";
        String image = "https://cataas.com/cat";

        // when
        User user = User.registration(socialLoginId, username, image);

        // then
        assertThat(user.getDisplayName()).isEqualTo("김기가#6789");
    }


}