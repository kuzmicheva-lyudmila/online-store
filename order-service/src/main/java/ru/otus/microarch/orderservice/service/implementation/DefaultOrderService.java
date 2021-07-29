package ru.otus.microarch.orderservice.service.implementation;

import com.mongodb.client.result.UpdateResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.microarch.orderservice.model.Order;
import ru.otus.microarch.orderservice.model.OrderStatus;
import ru.otus.microarch.orderservice.service.OrderService;

import java.util.UUID;

import static ru.otus.microarch.orderservice.kafka.CommonConfiguration.CANCEL_ORDER_KAFKA_TOPIC_NAME;
import static ru.otus.microarch.orderservice.kafka.CommonConfiguration.CREATE_ORDER_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Getter
public class DefaultOrderService implements OrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MongoTemplate mongoTemplate;
    private final String topicName = CANCEL_ORDER_KAFKA_TOPIC_NAME;

    @Override
    @Transactional
    public Order createOrder(String content) {
        String orderId = UUID.randomUUID().toString();

        Order order = mongoTemplate.insert(new Order(orderId, content));
        kafkaTemplate.send(CREATE_ORDER_KAFKA_TOPIC_NAME, orderId, content);
        return order;
    }

    @Override
    @Transactional
    @KafkaListener(topics = "#{__listener.getTopicName()}")
    public boolean cancelOrder(String orderId) {
        UpdateResult updateResult = mongoTemplate.updateFirst(
                Query.query(Criteria.where("orderId").is(orderId)),
                new Update().set("status", OrderStatus.CANCEL),
                Order.class
        );

        return updateResult.wasAcknowledged();
    }

    @Override
    public Order getOrder(String orderId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("orderId").is(orderId)), Order.class);
    }
}
