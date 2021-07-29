package ru.otus.microarch.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    private String id;

    private OrderStatus status;

    private String content;

    private long createDateSec;

    public Order(String id, String content) {
        this.id = id;
        this.content = content;
        this.status = OrderStatus.SUCCESS;
        this.createDateSec = Instant.now().getEpochSecond();
    }
}
