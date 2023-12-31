package com.gigacoffeebackend.auth.dto;

import lombok.Getter;

@Getter
public class AccessAndRefreshToken {
    private final String accessToken;
    private final String refreshToken;

    public AccessAndRefreshToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
