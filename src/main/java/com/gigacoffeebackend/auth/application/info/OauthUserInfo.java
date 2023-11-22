package com.gigacoffeebackend.auth.application.info;

public interface OauthUserInfo {
    String getSocialLoginId();

    String getNickname();

    String getImageUrl();
}
