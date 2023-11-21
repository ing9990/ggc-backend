package com.gigacoffeebackend.user.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @DisplayName("유저의 소셜 아이디와 닉네임이 모두 같을 유저가 가입할 수 없다.")
    @Test
    void socialId_and_nickname_cannot_duplicate() {
        // given
        String socialId = "9876502";
        String nickname = "테스트임";
        유저를_저장한다(socialId, nickname);

        // when // then
        assertThatThrownBy(() -> userService.save(socialId, nickname, "https://hi.png"))
                .isInstanceOf(BusinessException.class);
    }


    @DisplayName("소셜 아이디와 닉네임으로 유저를 찾는다.")
    @Test
    void find_user_by_social_id_and_nickname() {
        // given
        String socialId = "9876502";
        String nickname = "테스트임";
        유저를_저장한다(socialId, nickname);

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

    private void 유저를_저장한다(String socialLoginId, String nickname) {
        userRepository.save(User.user(socialLoginId, nickname, "https://image.png"));
    }
}