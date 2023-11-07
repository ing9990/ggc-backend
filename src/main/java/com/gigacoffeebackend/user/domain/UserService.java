package com.gigacoffeebackend.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findBySocialLoginIdAndNickName(String socialLoginId, String nickname) {
        return userRepository.findBySocialLoginIdAndNickName(socialLoginId, nickname);
    }

    public User save(String socialLoginId, String nickname, String imageUrl) {
        return userRepository.save(User.registration(socialLoginId, nickname, imageUrl));
    }

    public Optional<User> findUserByuserId(Long userId) {
        return userRepository.findUserById(userId);
    }

    public User getUserByUserId(Long userId) {
        return findUserByuserId(userId).orElseThrow(UserNotFoundException::new);
    }
}
