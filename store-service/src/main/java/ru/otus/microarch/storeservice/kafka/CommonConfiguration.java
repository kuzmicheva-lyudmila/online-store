package ru.otus.microarch.storeservice.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommonConfiguration {

    public static String CREATE_RESERVE_KAFKA_TOPIC_NAME = "create_reserve";
    public static String CANCEL_RESERVE_KAFKA_TOPIC_NAME = "cancel_reserve";
    public static String CREATE_ORDER_KAFKA_TOPIC_NAME = "create_order";
    public static String CANCEL_ORDER_KAFKA_TOPIC_NAME = "cancel_order";

    @Value(value = "${spring.kafka.bootstrap-address}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createReserveTopic() {
        return new NewTopic(CREATE_RESERVE_KAFKA_TOPIC_NAME, 1, (short) 1);
    }

    @Bean
    public NewTopic cancelReserveTopic() {
        return new NewTopic(CANCEL_RESERVE_KAFKA_TOPIC_NAME, 1, (short) 1);
    }
}
