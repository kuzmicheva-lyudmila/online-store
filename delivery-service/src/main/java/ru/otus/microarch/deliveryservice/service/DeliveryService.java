package ru.otus.microarch.deliveryservice.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ru.otus.microarch.deliveryservice.kafka.ConsumerConfiguration.CREATE_PAYMENT_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class DeliveryService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String createPaymentTopicName = CREATE_PAYMENT_KAFKA_TOPIC_NAME;

    @Transactional
    @KafkaListener(topics = "#{__listener.getCreatePaymentTopicName()}")
    public void createDelivery(ConsumerRecord<String, String> record) {
        log.info("DELIVERY: key = {}, value = {}", record.key(), record.value());
    }
}
