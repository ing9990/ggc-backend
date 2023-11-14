package com.gigacoffeebackend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.auth.domain.RefreshToken;
import com.gigacoffeebackend.auth.domain.RefreshTokenRepository;
import com.gigacoffeebackend.global.aop.BearerAuthorizationExtractor;
import com.gigacoffeebackend.login.infra.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
public abstract class ApiTest {

    @Autowired
    protected ObjectMapper om;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private JwtProvider jwtProvider;


    @MockBean
    protected BearerAuthorizationExtractor bearerExtractor;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;


    @BeforeEach
    void setUp() {
        doNothing()
                .when(bearerExtractor.extractAccessToken(any()));
        doNothing()
                .when(jwtProvider.isValidRefreshAndValidAccess(any(), any()));
    }
}
