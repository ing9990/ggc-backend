package com.gigacoffeebackend.auth.domain;

import com.gigacoffeebackend.global.dto.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken extends BaseEntity {

    @Id
    private String token;

    @Column(nullable = false, unique = true)
    private Long userId;

    public RefreshToken(final String token, final Long userId) {
        this.token = token;
        this.userId = userId;
    }
}
