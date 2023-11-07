package com.gigacoffeebackend.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("SocialLoginId와 닉네임으로 유저를 특정할 수 있다.")
    @Test
    void findBySocialLoginIdAndNickName() {
        // given
        String socialLoginId = "123456789";
        String nickname = "김기가";

        // when
        User result = userRepository.findBySocialLoginIdAndNickName(socialLoginId, nickname);

        // then
        Assertions.assertThat(result.getSocialLoginId()).isEqualTo(socialLoginId);
        Assertions.assertThat(result.getNickName()).isEqualTo(nickname);
    }

    @DisplayName("UserId로 유저를 찾을 수 있다.")
    @Test
    void findUserById() {
        // given // when
        Optional<User> result = userRepository.findUserById(1L);

        // then
        Assertions.assertThat(result).isPresent();
        User resultUser = result.get();
        Assertions.assertThat(resultUser.getSocialLoginId()).isEqualTo("123456789");
        Assertions.assertThat(resultUser.getNickName()).isEqualTo("김기가");
    }
}