package com.eCommerce.orders_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrdersRequestDto {
    private Long id;
    private List<OrderItemRequestDto> items;
    private Double totalPrice;

}
