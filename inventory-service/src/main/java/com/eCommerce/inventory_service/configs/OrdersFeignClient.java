package com.eCommerce.inventory_service.configs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "orders-service",path = "/orders", url = "${ORDER_SERVICE_URI:}")
public interface OrdersFeignClient {
    @GetMapping("/core/helloOrders")
    String fetchOrders();
}
