package com.gigacoffeebackend.login.application.oauth.info;

public interface OauthUserInfo {
    String getSocialLoginId();

    String getNickname();

    String getImageUrl();
}
