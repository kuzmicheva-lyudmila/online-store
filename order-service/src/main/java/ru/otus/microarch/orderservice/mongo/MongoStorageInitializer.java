package ru.otus.microarch.orderservice.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ru.otus.microarch.orderservice.model.Order;

@Component
@RequiredArgsConstructor
public class MongoStorageInitializer {

    private final MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void checkInitStorage() {
        if (!mongoTemplate.collectionExists(Order.class)) {
            mongoTemplate.createCollection(Order.class);
        }
    }
}
