package com.gigacoffeebackend.store.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode(of = {"name"})
public class StoreName {

    @Column(name = "name", nullable = false)
    private String name;

    public StoreName(String name) {
        this.name = name;
    }

    protected StoreName() {

    }

    public String toString() {
        return name;
    }
}
