package com.gigacoffeebackend.category.dto;

import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class CategoryNames {

    private final Set<String> categories = new HashSet<>();

    public Optional<String> find(final String name) {
        return categories.stream().filter(item -> item.equals(name)).findFirst();
    }

    public CategoryNames addAll(final Collection<String> names) {
        categories.addAll(names);
        return this;
    }
}