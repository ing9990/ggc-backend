package com.gigacoffeebackend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AccessorTest {

    @DisplayName("로그인 정보가 있다면 로그인된 유저이고, UserId는 해당 유저의 ID로 할당된다.")
    @Test
    void user() {
        // given
        long userId = 1L;

        // when
        Accessor guest = Accessor.user(userId);

        // then
        assertThat(guest.getUserId()).isEqualTo(1L);
        assertThat(guest.getAuthority()).isEqualTo(Authority.USER);
    }


}