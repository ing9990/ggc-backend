package com.gigacoffeebackend.store.event;

import com.gigacoffeebackend.store.domain.Store;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StoreSavedEvent extends ApplicationEvent {

    public StoreSavedEvent(Store store) {
        super(store);
    }
}
