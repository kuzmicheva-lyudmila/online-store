package ru.otus.microarch.storeservice.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.microarch.storeservice.model.Reserve;

import static ru.otus.microarch.storeservice.kafka.CommonConfiguration.CANCEL_ORDER_KAFKA_TOPIC_NAME;
import static ru.otus.microarch.storeservice.kafka.CommonConfiguration.CANCEL_RESERVE_KAFKA_TOPIC_NAME;
import static ru.otus.microarch.storeservice.kafka.CommonConfiguration.CREATE_ORDER_KAFKA_TOPIC_NAME;
import static ru.otus.microarch.storeservice.kafka.CommonConfiguration.CREATE_RESERVE_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class StoreService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MongoTemplate mongoTemplate;

    private final String createOrderTopicName = CREATE_ORDER_KAFKA_TOPIC_NAME;
    private final String cancelReserveTopicName = CANCEL_RESERVE_KAFKA_TOPIC_NAME;

    @Transactional
    @KafkaListener(topics = "#{__listener.getCreateOrderTopicName()}")
    public void createReserve(ConsumerRecord<String, String> record) {
        String orderId = record.key();
        String content = record.value();
        try {
            mongoTemplate.insert(new Reserve(orderId, content));
            kafkaTemplate.send(CREATE_RESERVE_KAFKA_TOPIC_NAME, orderId, content);
        } catch (Exception e) {
            log.error("Error occurred while creating reserve", e);
            kafkaTemplate.send(CANCEL_ORDER_KAFKA_TOPIC_NAME, orderId);
        }

    }

    @Transactional
    @KafkaListener(topics = "#{__listener.getCancelReserveTopicName()}")
    public void cancelReserve(String orderId) {
        mongoTemplate.remove(Query.query(Criteria.where("orderId").is(orderId)), Reserve.class);
        kafkaTemplate.send(CANCEL_ORDER_KAFKA_TOPIC_NAME, orderId);
    }
}
