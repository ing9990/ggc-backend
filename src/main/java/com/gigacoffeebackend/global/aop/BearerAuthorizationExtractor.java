package com.gigacoffeebackend.global.aop;

import com.gigacoffeebackend.global.exceptions.ErrorCode;
import com.gigacoffeebackend.login.application.InvalidJwtException;
import org.springframework.stereotype.Component;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.INVALID_BEARER;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.JWT_INVALID;

@Component
public class BearerAuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public String extractAccessToken(String header) {
        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.substring(BEARER_TYPE.length()).trim();
        }
        throw new InvalidJwtException(INVALID_BEARER);
    }
}
