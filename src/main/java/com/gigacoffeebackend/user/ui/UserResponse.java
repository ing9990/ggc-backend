package com.gigacoffeebackend.user.ui;

import com.gigacoffeebackend.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {

    private String displayName;

    private String profileImageUrl;

    private String socialLoginId;

    private String nickName;

    @Builder
    private UserResponse(String socialLoginId, String nickName, String displayName, String profileImageUrl) {
        this.socialLoginId = socialLoginId;
        this.nickName = nickName;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .displayName(user.getDisplayName())
                .profileImageUrl(user.getImageUrl())
                .socialLoginId(user.getSocialLoginId())
                .nickName(user.getNickName())
                .build();
    }
}
