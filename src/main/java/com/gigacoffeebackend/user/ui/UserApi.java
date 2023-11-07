package com.gigacoffeebackend.user.ui;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.global.aop.annotation.CurrentUser;
import com.gigacoffeebackend.user.domain.Accessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gigacoffeebackend.global.dto.ApiResponse.ok;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApi {

    private final IntegrateUserService integrateUserService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me(
            @CurrentUser Accessor accessor
    ) {
        return status(OK).body(ok(integrateUserService.findUserByAccesoor(accessor)));
    }
}
