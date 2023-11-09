package com.gigacoffeebackend.auth.ui;

import com.gigacoffeebackend.auth.domain.AccessAndRefreshToken;
import com.gigacoffeebackend.auth.infra.JwtProvider;
import com.gigacoffeebackend.auth.infra.oauth.info.OauthUserInfo;
import com.gigacoffeebackend.auth.infra.oauth.providers.OAuthProviderPort;
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
public class AuthLogin {

    private final OAuthProviderMapper OAuthProviderMapper;
    private final IntegrateUserService integrateUserService;
    private final JwtProvider jwtProvider;

    public AccessAndRefreshToken connect(final String providerName, final String token) {
        final OAuthProviderPort provider = OAuthProviderMapper.mapping(providerName);
        final OauthUserInfo oauthUserInfo = provider.getUserInfo(token);
        log.info("Now connect socialid, Nickname is " + oauthUserInfo.getSocialLoginId() + ":" + oauthUserInfo.getNickname());
        User user = ofNullable(integrateUserService.findUser(
                oauthUserInfo.getSocialLoginId(),
                oauthUserInfo.getNickname()
        )).orElseGet(() -> integrateUserService.registerUser(
                oauthUserInfo.getSocialLoginId(),
                oauthUserInfo.getNickname(),
                oauthUserInfo.getImageUrl()
        ));
        return jwtProvider.generateLoginToken(user.getId().toString());
    }
}
