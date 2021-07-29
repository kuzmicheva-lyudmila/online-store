package ru.otus.microarch.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.microarch.orderservice.model.Order;
import ru.otus.microarch.orderservice.service.OrderService;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class MainController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody String content) {
        return orderService.createOrder(content);
    }

    @GetMapping("/{orderID}")
    public Order getOrder(@PathVariable("orderId") String orderID) {
        return orderService.getOrder(orderID);
    }
}
