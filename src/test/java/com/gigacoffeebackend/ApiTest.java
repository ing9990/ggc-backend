package com.gigacoffeebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.global.aop.BearerAuthorizationExtractor;
import com.gigacoffeebackend.login.infra.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


public class ApiTest {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private BearerAuthorizationExtractor bearerAuthorizationExtractor;

}
