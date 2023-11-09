package com.gigacoffeebackend.auth.ui;

import com.gigacoffeebackend.auth.domain.AuthService;
import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.login.ui.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth/token")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    public static final int COOKIE_AGE_SECONDS = 604800;
    public static final String REFRESH_TOKEN = "refresh-token";

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> extendToken(
            @CookieValue(name = "refresh-token") final String refreshToken,
            @RequestHeader(AUTHORIZATION) final String accessToken,
            HttpServletResponse response
    ) {
        String accessAndRefreshToken = authService.renewalAccessToken(refreshToken, accessToken);

        return ResponseEntity.status(CREATED).body(ApiResponse.ok(new AccessTokenResponse(accessAndRefreshToken)));
    }

}
