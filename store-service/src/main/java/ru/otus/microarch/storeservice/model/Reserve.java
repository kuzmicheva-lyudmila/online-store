package ru.otus.microarch.storeservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("reserves")
@Data
@NoArgsConstructor
public class Reserve {

    @Id
    private String id;

    private String orderId;

    private String content;

    public Reserve(String orderId, String content) {
        this.orderId = orderId;
        this.content = content;
    }
}
