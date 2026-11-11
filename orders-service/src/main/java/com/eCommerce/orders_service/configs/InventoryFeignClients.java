package com.eCommerce.orders_service.configs;

import com.eCommerce.orders_service.dtos.OrdersRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service",path = "/inventory",url = "${INVENTORY_SERVICE_URL:}")
public interface InventoryFeignClients {
    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(@RequestBody OrdersRequestDto ordersRequestDto);
}
