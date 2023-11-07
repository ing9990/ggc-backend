package com.gigacoffeebackend.user.ui;

import com.gigacoffeebackend.user.domain.Accessor;
import com.gigacoffeebackend.user.domain.User;
import com.gigacoffeebackend.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gigacoffeebackend.user.ui.UserResponse.fromUser;

@Service
@Transactional
@RequiredArgsConstructor
public class IntegrateUserService {

    private final UserService userService;

    public User findUser(String socialLoginId, String nickname) {
        return userService.findBySocialLoginIdAndNickName(socialLoginId, nickname);
    }

    public User registerUser(String socialLoginId, String nickname, String imageUrl) {
        return userService.save(socialLoginId, nickname, imageUrl);
    }

    public UserResponse findUserByAccesoor(Accessor accessor) {
        return fromUser(userService.getUserByUserId(accessor.getUserId()));
    }
}
