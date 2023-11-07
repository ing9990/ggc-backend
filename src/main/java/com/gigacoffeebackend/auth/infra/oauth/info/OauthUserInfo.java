package com.gigacoffeebackend.auth.infra.oauth.info;

public interface OauthUserInfo {
    String getSocialLoginId();

    String getNickname();

    String getImageUrl();
}
