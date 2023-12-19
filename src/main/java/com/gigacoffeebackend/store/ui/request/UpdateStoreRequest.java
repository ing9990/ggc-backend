package com.gigacoffeebackend.store.ui.request;

import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.StoreName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class UpdateStoreRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    public StoreName toStoreName() {
        return new StoreName(name);
    }

    public LocationName toLocation() {
        return new LocationName(location);
    }
}
