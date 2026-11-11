package com.eCommerce.orders_service.controllers;

import com.eCommerce.orders_service.configs.FeaturesEnabledConfig;
import com.eCommerce.orders_service.dtos.OrdersRequestDto;
import com.eCommerce.orders_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class OrdersController {
    private final ModelMapper modelMapper;
    private final OrdersService ordersService;
    private final FeaturesEnabledConfig featuresEnabledConfig;

    @Value("${my.variable}")
    private String vals;

    @GetMapping("/helloOrders")
    public String helloOrders()
    {
        if(featuresEnabledConfig.isFeatureEnabled())
        {
            return "FeatureEnabled";
        }
        else {
            return "NoFeatureEnabled";
        }
    }

    @GetMapping
    private ResponseEntity<List<OrdersRequestDto>> getAllOrders()
    {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    @GetMapping(path = "/{id}")
    private ResponseEntity<OrdersRequestDto> getOrdersById(@PathVariable Long id)
    {
        return ResponseEntity.ok(ordersService.getOrdersById(id));
    }

    @PostMapping("/create-orders")
    private ResponseEntity<OrdersRequestDto> createOrders(@RequestBody OrdersRequestDto ordersRequestDto)
    {
        return ResponseEntity.ok(ordersService.createOrders(ordersRequestDto));
    }
}
