package com.gigacoffeebackend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


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
        유저가_생성됨(socialLoginId, nickname, "https://image.png");

        // when
        User result = userRepository.findBySocialLoginIdAndNickName(socialLoginId, nickname);

        // then
        assertThat(result.getSocialLoginId()).isEqualTo(socialLoginId);
        assertThat(result.getNickName()).isEqualTo(nickname);
    }

    @DisplayName("UserId로 유저를 찾을 수 있다.")
    @Test
    void findUserById() {
        // given // when
        String socialLoginId = "123456789";
        String nickname = "김기가";
        User user = 유저가_생성됨(socialLoginId, nickname, "https://image.png");
        Optional<User> result = userRepository.findUserById(user.getId());

        // then
        assertThat(result).isPresent();
        User resultUser = result.get();
        assertThat(resultUser.getSocialLoginId()).isEqualTo("123456789");
        assertThat(resultUser.getNickName()).isEqualTo("김기가");
    }

    protected User 유저가_생성됨(String socialLoginId, String nickName, String imageUrl) {
        return userRepository.save(User.user(socialLoginId, nickName, imageUrl));
    }
}