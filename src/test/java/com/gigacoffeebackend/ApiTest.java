package com.gigacoffeebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.auth.domain.AccessAndRefreshToken;
import com.gigacoffeebackend.auth.domain.RefreshTokenRepository;
import com.gigacoffeebackend.global.aop.BearerAuthorizationExtractor;
import com.gigacoffeebackend.login.infra.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


public abstract class ApiTest {

    public static final AccessAndRefreshToken TOKENS = new AccessAndRefreshToken("Access-Token", "Refresh-Token");

    @MockBean
    protected JwtProvider jwtProvider;

    @MockBean
    protected BearerAuthorizationExtractor bearerAuthorizationExtractor;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper om;
}
