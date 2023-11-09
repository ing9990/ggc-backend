package com.gigacoffeebackend.global.aop;

import com.gigacoffeebackend.login.ui.AuthException;
import com.gigacoffeebackend.login.application.RefreshTokenException;
import com.gigacoffeebackend.global.aop.annotation.CurrentUser;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import com.gigacoffeebackend.user.domain.Accessor;
import com.gigacoffeebackend.login.application.JwtProvider;
import com.gigacoffeebackend.auth.domain.RefreshTokenRepository;
import com.gigacoffeebackend.global.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.INVALID_REQUEST;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.JWT_REFRESH_NOT_FOUND;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private static final String REFRESH_TOKEN = "refresh-token";

    private final JwtProvider jwtProvider;
    private final BearerAuthorizationExtractor extractor;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.withContainingClass(Long.class)
                .hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Accessor resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new BadRequestException(INVALID_REQUEST);
        }
        try {
            final String accessToken = extractor.extractAccessToken(webRequest.getHeader(AUTHORIZATION));
            jwtProvider.validateToken(accessToken);
            final Long userId = Long.valueOf(jwtProvider.getSubject(accessToken));
            return Accessor.user(userId);
        } catch (final RefreshTokenException e) {
            throw new AuthException(ErrorCode.OAUTH_INVALID_TOKEN);
        }
    }

    private String extractRefreshToken(final Cookie... cookies) {
        if (cookies == null) {
            throw new RefreshTokenException(JWT_REFRESH_NOT_FOUND);
        }
        return Arrays.stream(cookies)
                .filter(this::isValidRefreshToken)
                .findFirst()
                .orElseThrow(() -> new RefreshTokenException(JWT_REFRESH_NOT_FOUND))
                .getValue();
    }

    private boolean isValidRefreshToken(final Cookie cookie) {
        return REFRESH_TOKEN.equals(cookie.getName()) &&
                refreshTokenRepository.existsByToken(cookie.getValue());
    }
}
