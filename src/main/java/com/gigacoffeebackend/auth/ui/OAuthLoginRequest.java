package com.gigacoffeebackend.auth.ui;

import lombok.*;
import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
class OAuthLoginRequest {

    @NotBlank(message = "소셜 로그인 아이디는 필수 값입니다.")
    private String socialLoginId;

    @NotBlank(message = "닉네임은 필수 값입니다.")
    private String nickname;

    private String image;
}
