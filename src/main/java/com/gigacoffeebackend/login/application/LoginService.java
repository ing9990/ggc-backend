package com.gigacoffeebackend.login.application;

import com.gigacoffeebackend.auth.domain.AccessAndRefreshToken;
import com.gigacoffeebackend.auth.application.AuthService;
import com.gigacoffeebackend.login.application.OAuthProviderMapper;
import com.gigacoffeebackend.login.application.info.OauthUserInfo;
import com.gigacoffeebackend.login.application.providers.OAuthProviderPort;
import com.gigacoffeebackend.user.domain.User;
import com.gigacoffeebackend.user.application.IntegrateUserService;
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

    private final OAuthProviderMapper oAuthProviderMapper;
    private final IntegrateUserService integrateUserService;

    private final AuthService authService;

    public AccessAndRefreshToken connect(final String providerName, final String code) {
        final OAuthProviderPort provider = oAuthProviderMapper.mapping(providerName);
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
