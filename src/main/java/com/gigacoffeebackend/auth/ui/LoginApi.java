package com.gigacoffeebackend.auth.ui;

import com.gigacoffeebackend.auth.dto.AccessAndRefreshToken;
import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.auth.application.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginApi {

    public static final int COOKIE_AGE_SECONDS = 604800;
    public static final String REFRESH_TOKEN = "refresh-token";
    private final LoginService loginService;

    @GetMapping("/login/{provider}")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> loginWithOAuth(
        @PathVariable final String provider, @RequestParam(value = "code") String code,
        final HttpServletResponse response) {
        final AccessAndRefreshToken accessAndRefreshToken = loginService.connect(provider, code);

        final ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN,
                accessAndRefreshToken.getRefreshToken()).maxAge(COOKIE_AGE_SECONDS).sameSite("None")
            .secure(true).httpOnly(false).path("/").build();
        response.addHeader(SET_COOKIE, cookie.toString());

        return ResponseEntity.status(CREATED)
            .body(ApiResponse.ok(new AccessTokenResponse(accessAndRefreshToken.getAccessToken())));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> loginWithInfo(
        @Valid @RequestBody final OAuthLoginRequest oAuthLoginRequest,
        final HttpServletResponse response) {
        final AccessAndRefreshToken accessAndRefreshToken = loginService.connect(
            oAuthLoginRequest.getSocialLoginId(), oAuthLoginRequest.getNickname(),
            oAuthLoginRequest.getImage());

        final ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN,
                accessAndRefreshToken.getRefreshToken()).maxAge(COOKIE_AGE_SECONDS).sameSite("None")
            .secure(true).httpOnly(false).path("/").build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return ResponseEntity.status(CREATED)
            .body(ApiResponse.ok(new AccessTokenResponse(accessAndRefreshToken.getAccessToken())));
    }

}
