package com.gigacoffeebackend.store.event;

import com.gigacoffeebackend.store.domain.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
@Slf4j
@Transactional(propagation = REQUIRES_NEW, readOnly = true)
public class StoreEventListener {

    @EventListener(StoreSavedEvent.class)
    public void handleStoreSavedEvent(StoreSavedEvent event) {
        Store store = (Store) event.getSource();

        log.info(StoreSavedEventFormat.saved(store.getFullName(), event.getTimestamp()));
    }
}
