package com.eCommerce.orders_service.dtos;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long id;
    private Integer quantity;
    private Long productId;
}
