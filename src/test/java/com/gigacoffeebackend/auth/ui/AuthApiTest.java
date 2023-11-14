package com.gigacoffeebackend.auth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.auth.application.AuthService;
import com.gigacoffeebackend.auth.domain.RefreshTokenRepository;
import com.gigacoffeebackend.global.aop.BearerAuthorizationExtractor;
import com.gigacoffeebackend.login.infra.JwtProvider;
import com.gigacoffeebackend.util.ApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthApi.class)
class AuthApiTest extends ApiTest {

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        /*  하다보니 막힘 생각할 것들
            1. AOP 어떻게 테스트 할 것인지
         */

        given(authService.renewalAccessToken(any(), any()))
                .willReturn("new ey.abcd");
    }
//
//    @DisplayName("토큰을 연장한다.")

//    @Test
//    void extend_token_with_refresh_token() throws Exception {
//        // given
//        String refreshToken = "ey.abcefg";
//        String requestHeader = "Bearer ey.asdt2312";
//
//
//        // when
//        mockMvc.perform(post("/api/v1/auth/token")
//                        .contentType(APPLICATION_JSON)
//                        .cookie(new Cookie("refresh-token", "ey.refresh.token")))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(
//                    jsonPath("$.status").value("OK"),
//                        jsonPath("$.data").value(
//                                om.readValue()
//                        )
//                );
//
//
//        // then
//    }


}