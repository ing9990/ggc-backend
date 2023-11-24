package com.gigacoffeebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.auth.dto.AccessAndRefreshToken;
import com.gigacoffeebackend.auth.domain.RefreshTokenRepository;
import com.gigacoffeebackend.global.aop.BearerAuthorizationExtractor;
import com.gigacoffeebackend.auth.application.infra.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


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

    @BeforeEach
    void setUp() {
        doNothing()
                .when(jwtProvider)
                .validateToken(any());

        doNothing()
                .when(jwtProvider)
                .validateTokens(any());

    }
}
