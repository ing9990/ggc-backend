package com.gigacoffeebackend.login.infra.oauth.info;

public interface OauthUserInfo {
    String getSocialLoginId();

    String getNickname();

    String getImageUrl();
}
