package com.gigacoffeebackend.login.service;

import com.gigacoffeebackend.auth.domain.AccessAndRefreshToken;
import com.gigacoffeebackend.auth.domain.AuthService;
import com.gigacoffeebackend.login.application.oauth.OAuthProviderMapper;
import com.gigacoffeebackend.login.application.oauth.info.OauthUserInfo;
import com.gigacoffeebackend.login.application.oauth.providers.OAuthProviderPort;
import com.gigacoffeebackend.user.domain.User;
import com.gigacoffeebackend.user.ui.IntegrateUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final OAuthProviderMapper OauthProviderMappper;
    private final IntegrateUserService integrateUserService;

    private final AuthService authService;

    public AccessAndRefreshToken connect(final String providerName, final String code) {
        final OAuthProviderPort provider = OauthProviderMappper.mapping(providerName);
        final OauthUserInfo oauthUserInfo = provider.getUserInfo(code);

        return connect(oauthUserInfo.getSocialLoginId(), oauthUserInfo.getNickname(), oauthUserInfo.getImageUrl());
    }

    public AccessAndRefreshToken connect(String socialLoginId, String nickname, String image) {
        log.info("Now connect socialid, Nickname is " + socialLoginId + ":" + nickname);
        User user = ofNullable(integrateUserService.findUser(socialLoginId, nickname))
                .orElseGet(() -> integrateUserService.registerUser(socialLoginId, nickname, image));
        return authService.generateToken(user.getId());
    }
}
