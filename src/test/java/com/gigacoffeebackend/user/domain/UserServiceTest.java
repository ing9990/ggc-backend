package com.gigacoffeebackend.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("소셜ID와 닉네임으로 유저를 찾는다.")
    @Test
    void find_user_by_social_id_and_nickname() {
        // given
        String socialId = "9876502";
        String nickname = "테스트임";
        saveUser(socialId, nickname);

        // when
        User result = userRepository.findBySocialLoginIdAndNickName(socialId, nickname);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getNickName()).isEqualTo(nickname);
        Assertions.assertThat(result.getAuthority()).isEqualTo(Authority.USER);
        Assertions.assertThat(result.getSocialLoginId()).isEqualTo(socialId);
        String displayName = nickname + "#" + socialId.substring(socialId.length() - 4);
        Assertions.assertThat(result.getDisplayName()).isEqualTo(displayName);
    }

    User saveUser(String socialLoginId, String nickname) {
        User savedUser = userRepository.save(User.user(socialLoginId, nickname, "https://imageimage.png"));
        return savedUser;
    }
}