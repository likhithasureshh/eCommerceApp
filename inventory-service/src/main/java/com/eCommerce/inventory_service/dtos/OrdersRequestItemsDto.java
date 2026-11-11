package com.eCommerce.inventory_service.dtos;

import lombok.Data;

@Data
public class OrdersRequestItemsDto {
    private Long id;
    private Long productId;
    private Integer quantity;
}
