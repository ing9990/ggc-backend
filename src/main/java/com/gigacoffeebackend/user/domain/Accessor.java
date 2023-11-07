package com.gigacoffeebackend.user.domain;

import lombok.Getter;

import static com.gigacoffeebackend.user.domain.Authority.*;

@Getter
public class Accessor {

    private final Long userId;
    private final Authority authority;

    private Accessor(final Long userId, final Authority authority) {
        this.userId = userId;
        this.authority = authority;
    }

    public static Accessor user(final Long userId) {
        return new Accessor(userId, USER);
    }

    public boolean isUser() {
        return USER.equals(authority);
    }
}
