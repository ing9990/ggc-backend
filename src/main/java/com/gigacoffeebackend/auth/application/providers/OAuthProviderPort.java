package com.gigacoffeebackend.auth.application.providers;

import com.gigacoffeebackend.auth.application.info.OauthUserInfo;
import org.springframework.web.client.RestTemplate;

public interface OAuthProviderPort {

    RestTemplate restTemplate = new RestTemplate();

    boolean is(String name);
    OauthUserInfo getUserInfo(String code);
}
