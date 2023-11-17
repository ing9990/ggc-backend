package com.gigacoffeebackend.option.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductOptionSelect {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private ProductOption option;

    private String value;

    // N 개 중 한개를 고르는 옵션일 수도 있고
    // 단순 숫자 혹은 문자를 입력하는 옵션일 수도 있다.
}
