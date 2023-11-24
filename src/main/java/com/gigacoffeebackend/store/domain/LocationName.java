package com.gigacoffeebackend.store.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode(of = {"locationName"})
public class LocationName {

    @Column(name = "location_name", nullable = false)
    private String locationName;

    public LocationName(String locationName) {
        this.locationName = locationName;
    }

    protected LocationName() {
    }

    public String toString() {
        return locationName;
    }
}
