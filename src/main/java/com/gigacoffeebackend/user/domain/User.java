package com.gigacoffeebackend.user.domain;

import com.gigacoffeebackend.global.dto.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String socialLoginId;

    @Column(nullable = false)
    private String nickName;

    @Column(name = "image_url", length = 4096)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    private User(String socialLoginId, String nickName, String imageUrl) {
        this.socialLoginId = socialLoginId;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.authority = Authority.USER;

        this.displayName = extractDisplayName(socialLoginId, nickName);
    }

    public static User user(String socialLoginId, String nickname, String imageUrl) {
        return new User(socialLoginId, nickname, imageUrl);
    }


    private String extractDisplayName(String socialLoginId, String nickName) {
        return nickName + "#" + socialLoginId.substring(socialLoginId.length() - 4);
    }
}