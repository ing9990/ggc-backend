package com.gigacoffeebackend.auth.ui;

import lombok.*;

import javax.persistence.Column;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class OAuthLoginRequest {

    @Column(name = "소셜 로그인 아이디는 필수 값입니다.")
    private String socialLoginId;
    
    @Column(name = "닉네임은 필수 값입니다.")
    private String nickname;

    private String image;
}
