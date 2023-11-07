package com.gigacoffeebackend.auth.infra.oauth.providers;

import com.gigacoffeebackend.auth.infra.oauth.info.OauthUserInfo;
import org.springframework.web.client.RestTemplate;

public interface OAuthProviderPort {

    RestTemplate restTemplate = new RestTemplate();

    boolean is(String name);
    OauthUserInfo getUserInfo(String code);
}
