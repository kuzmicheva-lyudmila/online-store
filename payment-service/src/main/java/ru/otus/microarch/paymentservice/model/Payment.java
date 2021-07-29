package ru.otus.microarch.paymentservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payments")
@Data
@NoArgsConstructor
public class Payment {

    @Id
    private String id;

    private String orderId;

    private String content;

    public Payment(String orderId, String content) {
        this.orderId = orderId;
        this.content = content;
    }
}
