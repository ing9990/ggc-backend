package com.gigacoffeebackend.auth.application.infra;

import com.gigacoffeebackend.auth.domain.AccessAndRefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JwtProvider {

    public static final String EMPTY_SUBJECT = "";

    private final SecretKey secretKey;

    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtProvider(
            @Value("${security.jwt.secret-key}") final String secretKey,
            @Value("${security.jwt.access-expiration-time}") final Long accessExpirationTime,
            @Value("${security.jwt.refresh-expiration-time}") final Long refreshExpirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));

        this.accessExpirationTime = accessExpirationTime * 1000;
        this.refreshExpirationTime = refreshExpirationTime * 1000;
    }

    public AccessAndRefreshToken generateLoginToken(final String subject) {
        final String accessToken = createToken(subject, accessExpirationTime);
        final String refreshToken = createToken(EMPTY_SUBJECT, refreshExpirationTime);

        return new AccessAndRefreshToken(accessToken, refreshToken);
    }

    private String createToken(final String subject, final Long validityInMilliseconds) {
        final long iap = System.currentTimeMillis();
        final long exp = iap + validityInMilliseconds;

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(new Date(iap))
                .setExpiration(new Date(exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateTokens(final AccessAndRefreshToken accessAndRefreshToken) {
        validateAccessToken(accessAndRefreshToken.getAccessToken());
        validateRefreshToken(accessAndRefreshToken.getRefreshToken());
    }

    private void validateRefreshToken(final String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (final ExpiredJwtException e) {
            throw new ExpiredPeriodJwtException(JWT_EXPIRED_REF);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException(JWT_INVALID_REF);
        }
    }

    private void validateAccessToken(final String accessToken) {
        try {
            parseToken(accessToken);
        } catch (final ExpiredJwtException e) {
            throw new ExpiredPeriodJwtException(JWT_EXPIRED);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException(JWT_INVALID);
        }
    }

    public String regenerateAccessToken(final String subject) {
        return createToken(subject, accessExpirationTime);
    }

    public String getSubject(final String token) {
        return parseToken(token)
                .getBody()
                .getSubject();
    }

    private Jws<Claims> parseToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public void validateToken(String accessToken) {
        validateAccessToken(accessToken);
    }

    public boolean isValidRefreshAndValidAccess(final String refreshToken, final String accessToken) {
        try {
            validateRefreshToken(refreshToken);
            validateAccessToken(accessToken);
            return true;
        } catch (final JwtException e) {
            return false;
        }
    }
}
