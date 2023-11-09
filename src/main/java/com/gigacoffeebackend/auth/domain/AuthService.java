package com.gigacoffeebackend.auth.domain;

import com.gigacoffeebackend.global.aop.BearerAuthorizationExtractor;
import com.gigacoffeebackend.login.application.JwtProvider;
import com.gigacoffeebackend.login.ui.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.gigacoffeebackend.global.exceptions.ErrorCode.JWT_FAIL_TO_MAKE;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.JWT_INVALID_REF;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final BearerAuthorizationExtractor bearerExtractor;
    private final JwtProvider jwtProvider;

    public String renewalAccessToken(final String refreshTokenRequest, final String authorizationHeader) {
        final String accessToken = bearerExtractor.extractAccessToken(authorizationHeader);
        if (jwtProvider.isValidRefreshAndValidAccess(refreshTokenRequest, accessToken)) {
            final RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenRequest)
                    .orElseThrow(() -> new AuthException(JWT_INVALID_REF));
            return jwtProvider.regenerateAccessToken(refreshToken.getUserId().toString());
        }
        if (jwtProvider.isValidRefreshAndValidAccess(refreshTokenRequest, accessToken)) {
            return accessToken;
        }
        throw new AuthException(JWT_FAIL_TO_MAKE);
    }

    public void removeRefreshToken(final Long refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    public AccessAndRefreshToken generateToken(Long id) {
        removeRefreshToken(id);
        AccessAndRefreshToken tokens = jwtProvider.generateLoginToken(String.valueOf(id));
        refreshTokenRepository.save(new RefreshToken(tokens.getRefreshToken(), id));
        return tokens;
    }

}