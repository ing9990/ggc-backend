package com.gigacoffeebackend.login.application.oauth.providers;

import com.gigacoffeebackend.login.application.oauth.OauthAccessToken;
import com.gigacoffeebackend.login.application.oauth.info.GoogleUserInfo;
import com.gigacoffeebackend.login.application.oauth.info.OauthUserInfo;
import com.gigacoffeebackend.auth.AuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import static com.gigacoffeebackend.global.exceptions.ErrorCode.OAUTH_INVALID_TOKEN;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.OAUTH_SERVICE_NOT_FOUND;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
public class GoogleProviderAdapter implements OAuthProviderPort {
    private static final String PROVIDER_NAME = "google";

    protected final String clientId;
    protected final String clientSecret;
    protected final String redirectUri;
    protected final String tokenUri;
    protected final String userUri;

    public GoogleProviderAdapter(
            @Value("${oauth2.provider.google.client-id}") final String clientId,
            @Value("${oauth2.provider.google.client-secret}") final String clientSecret,
            @Value("${oauth2.provider.google.redirect-uri}") final String redirectUri,
            @Value("${oauth2.provider.google.token-uri}") final String tokenUri,
            @Value("${oauth2.provider.google.user-info}") final String userUri
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenUri = tokenUri;
        this.userUri = userUri;
    }

    @Override
    public boolean is(final String name) {
        return PROVIDER_NAME.equals(name);
    }

    @Override
    public OauthUserInfo getUserInfo(final String code) {
        final String accessToken = requestAccessToken(code);
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<MultiValueMap<String, String>> userInfoRequestEntity = new HttpEntity<>(headers);
        final ResponseEntity<GoogleUserInfo> response = restTemplate.exchange(
                userUri,
                HttpMethod.GET,
                userInfoRequestEntity,
                GoogleUserInfo.class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new AuthException(OAUTH_SERVICE_NOT_FOUND);
    }

    private String requestAccessToken(final String code) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        final HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setBasicAuth(clientId, clientSecret);
        httpHeaders.setContentType(APPLICATION_FORM_URLENCODED);
        httpHeaders.add("Accept-Charset", "UTF-8");

        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");
        final HttpEntity<MultiValueMap<String, String>> accessTokenRequestEntity = new HttpEntity<>(params, httpHeaders);
        final ResponseEntity<OauthAccessToken> accessTokenResponse = restTemplate.exchange(
                tokenUri,
                POST,
                accessTokenRequestEntity,
                OauthAccessToken.class
        );

        return ofNullable(accessTokenResponse.getBody())
                .orElseThrow(() -> new AuthException(OAUTH_INVALID_TOKEN))
                .getAccessToken();
    }
}
