package com.gigacoffeebackend.products.option.ui.request;

import static java.util.Optional.ofNullable;

import com.gigacoffeebackend.products.option.domain.OptionName;
import com.gigacoffeebackend.products.option.domain.OptionPrice;
import java.math.BigDecimal;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class AddOptionRequest {

    @NotBlank(message = "옵션 이름을 입력 해주세요.")
    private String optionName;

    private BigDecimal optionPrice;

    public boolean isFree() {
        return optionPrice == null || isPositive();
    }

    private boolean isPositive() {
        return optionPrice.signum() == 1;
    }

    public OptionName toOptionName() {
        return new OptionName(this.optionName);
    }

    public OptionPrice toOptionPrice() {
        return new OptionPrice(this.optionPrice);
    }

    public Optional<BigDecimal> mapOptionPrice(){
        return ofNullable(optionPrice);
    }
}
