package com.gigacoffeebackend.auth.application;

import com.gigacoffeebackend.auth.dto.AccessAndRefreshToken;
import com.gigacoffeebackend.auth.domain.RefreshToken;
import com.gigacoffeebackend.auth.domain.RefreshTokenRepository;
import com.gigacoffeebackend.global.aop.BearerAuthorizationExtractor;
import com.gigacoffeebackend.auth.application.infra.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.gigacoffeebackend.global.exceptions.ErrorCode.JWT_FAIL_TO_MAKE;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.JWT_INVALID_REF;

@Service
@RequiredArgsConstructor
@Transactional
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

    public void removeRefreshToken(final Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public AccessAndRefreshToken generateToken(Long id) {
        removeRefreshToken(id);
        AccessAndRefreshToken tokens = jwtProvider.generateLoginToken(String.valueOf(id));
        refreshTokenRepository.save(new RefreshToken(tokens.getRefreshToken(), id));
        return tokens;
    }
}