package com.gigacoffeebackend.login.infra.oauth.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@ToString
public class KakaoUserInfo implements OauthUserInfo {

    @JsonProperty("id")
    private String socialLoginId;
    @JsonProperty("properties")
    private Properties properties;

    @Override
    public String getSocialLoginId() {
        return socialLoginId;
    }

    @Override
    public String getNickname() {
        return properties.name;
    }

    @Override
    public String getImageUrl() {
        return properties.image;
    }

    @NoArgsConstructor(access = PRIVATE)
    private static class Properties {

        @JsonProperty("nickname")
        private String name;
        @JsonProperty("thumbnail_image")
        private String image;
    }
}
