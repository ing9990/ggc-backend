package com.gigacoffeebackend.login.application.oauth.providers;

import com.gigacoffeebackend.login.ui.AuthException;
import com.gigacoffeebackend.login.application.oauth.OauthAccessToken;
import com.gigacoffeebackend.login.application.oauth.info.KakaoUserInfo;
import com.gigacoffeebackend.login.application.oauth.info.OauthUserInfo;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.OAUTH_SERVICE_NOT_FOUND;
import static java.util.Optional.ofNullable;

@Component
public class KakaoProviderAdapter implements OAuthProviderPort {

    private static final String PROVIDER_NAME = "kakao";

    protected final String clientId;
    protected final String clientSecret;
    protected final String redirectUri;
    protected final String tokenUri;
    protected final String userUri;

    public KakaoProviderAdapter(
            @Value("${oauth2.provider.kakao.client-id}") final String clientId,
            @Value("${oauth2.provider.kakao.client-secret}") final String clientSecret,
            @Value("${oauth2.provider.kakao.redirect-uri}") final String redirectUri,
            @Value("${oauth2.provider.kakao.token-uri}") final String tokenUri,
            @Value("${oauth2.provider.kakao.user-info}") final String userUri
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
        final ResponseEntity<KakaoUserInfo> response = getKakaoUserInfo(accessToken);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new AuthException(OAUTH_SERVICE_NOT_FOUND);
    }

    private ResponseEntity<KakaoUserInfo> getKakaoUserInfo(String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("property_keys", "[\"properties.thumbnail_image\",\"properties.nickname\"]");
        final HttpEntity<MultiValueMap<String, Object>> userInfoRequestEntity = new HttpEntity<>(params, headers);
        return restTemplate.exchange(
                userUri,
                HttpMethod.GET,
                userInfoRequestEntity,
                KakaoUserInfo.class
        );
    }

    private String requestAccessToken(String code) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("code", code);
        params.add("client_id", clientId);
        params.add("grant_type", "authorization_code");

        final HttpEntity<MultiValueMap<String, String>> accessTokenRequestEntity = new HttpEntity<>(params, headers);

        final ResponseEntity<OauthAccessToken> accessTokenResponse = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                accessTokenRequestEntity,
                OauthAccessToken.class
        );

        return ofNullable(accessTokenResponse.getBody())
                .orElseThrow(() -> new AuthException(ErrorCode.OAUTH_INVALID_TOKEN))
                .getAccessToken();
    }
}
