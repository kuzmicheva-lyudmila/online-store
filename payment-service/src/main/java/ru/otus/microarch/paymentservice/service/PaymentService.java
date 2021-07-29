package ru.otus.microarch.paymentservice.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.microarch.paymentservice.model.Payment;

import static ru.otus.microarch.paymentservice.kafka.CommonConfiguration.CANCEL_RESERVE_KAFKA_TOPIC_NAME;
import static ru.otus.microarch.paymentservice.kafka.CommonConfiguration.CREATE_PAYMENT_KAFKA_TOPIC_NAME;
import static ru.otus.microarch.paymentservice.kafka.CommonConfiguration.CREATE_RESERVE_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class PaymentService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MongoTemplate mongoTemplate;

    private final String createReserveTopicName = CREATE_RESERVE_KAFKA_TOPIC_NAME;

    @Transactional
    @KafkaListener(topics = "#{__listener.getCreateReserveTopicName()}")
    public void createReserve(ConsumerRecord<String, String> record) {
        String orderId = record.key();
        String content = record.value();
        try {
            mongoTemplate.insert(new Payment(orderId, content));
            kafkaTemplate.send(CREATE_PAYMENT_KAFKA_TOPIC_NAME, orderId, content);
        } catch (Exception e) {
            log.error("Error occurred while creating payment", e);
            kafkaTemplate.send(CANCEL_RESERVE_KAFKA_TOPIC_NAME, orderId);
        }
    }
}
