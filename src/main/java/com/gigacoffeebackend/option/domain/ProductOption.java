package com.gigacoffeebackend.option.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    //  개인 텀블러 사용 여부 ...
    private String name;

    // 선택지
    @OneToMany
    private Set<ProductOptionSelect> optionSelects = new HashSet<>();
}
