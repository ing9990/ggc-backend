package com.gigacoffeebackend.auth.ui;

import com.gigacoffeebackend.global.exceptions.ErrorCode;
import com.gigacoffeebackend.auth.infra.oauth.providers.OAuthProviderPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OAuthProviderMapper {

    private final List<OAuthProviderPort> providers;

    public OAuthProviderMapper(final List<OAuthProviderPort> providers) {
        this.providers = providers;
    }

    public OAuthProviderPort mapping(final String providerName) {
        return providers.stream()
                .filter(provider -> provider.is(providerName))
                .findFirst()
                .orElseThrow(() -> new AuthException(ErrorCode.OAUTH_SERVICE_NOT_FOUND));
    }
}