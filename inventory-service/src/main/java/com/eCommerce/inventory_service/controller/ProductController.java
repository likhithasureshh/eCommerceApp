package com.eCommerce.inventory_service.controller;

import com.eCommerce.inventory_service.configs.OrdersFeignClient;
import com.eCommerce.inventory_service.dtos.OrdersRequestDto;
import com.eCommerce.inventory_service.dtos.ProductDto;
import com.eCommerce.inventory_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController
{
    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrders")
    public String callOrdersService(HttpServletRequest httpServletRequest)
    {
//        log.info(httpServletRequest.getHeader("X-Custom-Header"));
//        ServiceInstance instance = discoveryClient.getInstances("orders-service").getFirst();
//        return restClient.get()
//                .uri(instance.getUri()+"/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);
        return ordersFeignClient.fetchOrders();
    }
    @GetMapping
    private ResponseEntity<List<ProductDto>> findAllInventory()
    {
        return ResponseEntity.ok(productService.findAllInventory());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<ProductDto> findInventoryById(@PathVariable Long id)
    {
         return ResponseEntity.ok(productService.findInventoryById(id));
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrdersRequestDto ordersRequestDto)
    {
        return ResponseEntity.ok(productService.reduceStocks(ordersRequestDto));
    }
}
