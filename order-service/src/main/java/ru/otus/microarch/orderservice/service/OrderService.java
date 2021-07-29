package ru.otus.microarch.orderservice.service;

import ru.otus.microarch.orderservice.model.Order;

public interface OrderService {

    Order createOrder(String content);

    boolean cancelOrder(String orderId);

    Order getOrder(String orderId);
}
