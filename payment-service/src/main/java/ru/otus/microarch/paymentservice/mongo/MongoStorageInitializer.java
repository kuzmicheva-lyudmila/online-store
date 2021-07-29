package ru.otus.microarch.paymentservice.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ru.otus.microarch.paymentservice.model.Payment;

@Component
@RequiredArgsConstructor
public class MongoStorageInitializer {

    private final MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void checkInitStorage() {
        if (!mongoTemplate.collectionExists(Payment.class)) {
            mongoTemplate.createCollection(Payment.class);
        }
    }
}
