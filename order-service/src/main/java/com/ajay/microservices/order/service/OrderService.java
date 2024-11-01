package com.ajay.microservices.order.service;

import com.ajay.microservices.order.client.InventoryClient;
import com.ajay.microservices.order.dto.OrderRequest;
import com.ajay.microservices.order.model.Order;
import com.ajay.microservices.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .skuCode(orderRequest.skuCode())
                    .price(orderRequest.price())
                    .quantity(orderRequest.quantity())
                    .build();

            orderRepository.save(order);
        } else {
            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + " is out of stock");
        }
    }
}
