package com.gigacoffeebackend.auth.application.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@ToString
public class GoogleUserInfo implements OauthUserInfo {

    @Getter
    @JsonProperty("id")
    private String socialLoginId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;

    @Override
    public String getNickname() {
        return name;
    }

    @Override
    public String getImageUrl() {
        return picture;
    }
}
