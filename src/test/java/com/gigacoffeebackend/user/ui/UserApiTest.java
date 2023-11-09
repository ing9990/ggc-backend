package com.gigacoffeebackend.user.ui;

import com.gigacoffeebackend.login.application.JwtProvider;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@Import({JwtProvider.class})
@WebMvcTest(UserApi.class)
class UserApiTest {
    // 2023 행로그 보고 컨트롤러 테스트 작성

}