package com.gigacoffeebackend.store.event;

import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.function.BiFunction;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class StoreSavedEventFormat {
    private static final BiFunction<String, Date, String> FORMAT = (name, date) -> name + " has been saved at " + date;

    public static String saved(final String name, final long timestamp) {
        return FORMAT.apply(name, new Date(timestamp));
    }
}
