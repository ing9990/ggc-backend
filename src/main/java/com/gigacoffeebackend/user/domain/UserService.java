package com.gigacoffeebackend.user.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.USER_DUPLIACTED;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findBySocialLoginIdAndNickName(String socialLoginId, String nickname) {
        return userRepository.findBySocialLoginIdAndNickName(socialLoginId, nickname);
    }

    @Transactional
    public User save(String socialLoginId, String nickname, String imageUrl) {
        checkUserDuplicate(socialLoginId, nickname);
        return userRepository.save(User.user(socialLoginId, nickname, imageUrl));
    }

    public Optional<User> findUserByuserId(Long userId) {
        return userRepository.findUserById(userId);
    }

    public User getUserByUserId(Long userId) {
        return findUserByuserId(userId).orElseThrow(UserNotFoundException::new);
    }

    private void checkUserDuplicate(String socialLoginId, String nickname) {
        if (userRepository.existsBySocialLoginIdAndNickName(socialLoginId, nickname)) {
            throw new BusinessException(USER_DUPLIACTED);
        }
    }
}
