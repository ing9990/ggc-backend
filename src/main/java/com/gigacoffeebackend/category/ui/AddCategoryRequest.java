package com.gigacoffeebackend.category.ui;

import com.gigacoffeebackend.global.aop.annotation.AlphabetOnly;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AddCategoryRequest {

    @NotBlank(message = "카테고리 이름을 입력하세요.")
    @AlphabetOnly
    private String name;

    @NotBlank(message = "카테고리의 표시 명을 입력하세요.")
    private String displayName;

    private List<Long> products = new ArrayList<>();
}
