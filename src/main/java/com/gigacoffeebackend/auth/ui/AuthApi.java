package com.gigacoffeebackend.auth.ui;

import com.gigacoffeebackend.auth.domain.AccessAndRefreshToken;
import com.gigacoffeebackend.auth.dto.AccessTokenResponse;
import com.gigacoffeebackend.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthApi {

    public static final int COOKIE_AGE_SECONDS = 604800;
    private final AuthLogin authLogin;

    @GetMapping("/login/{provider}")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> login(
            @PathVariable final String provider,
            @RequestParam(value = "code") String code,
            final HttpServletResponse response
    ) {
        final AccessAndRefreshToken accessAndRefreshToken = authLogin.connect(provider, code);

        final ResponseCookie cookie = ResponseCookie.from("refresh-token", accessAndRefreshToken.getRefreshToken())
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(false)
                .path("/")
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());

        return ResponseEntity.status(CREATED).body(ApiResponse.ok(new AccessTokenResponse(accessAndRefreshToken.getAccessToken())));
    }



}
