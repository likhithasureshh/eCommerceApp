package com.eCommerce.inventory_service.service;

import com.eCommerce.inventory_service.dtos.OrdersRequestDto;
import com.eCommerce.inventory_service.dtos.OrdersRequestItemsDto;
import com.eCommerce.inventory_service.dtos.ProductDto;
import com.eCommerce.inventory_service.entity.Product;
import com.eCommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    public List<ProductDto> findAllInventory() {
        log.info("Fetching all the inventories");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();

    }
    public ProductDto findInventoryById(Long id)
    {
        Optional<Product> product = productRepository.findById(id);
        return product.map(product1 -> modelMapper.map(product, ProductDto.class))
                .orElseThrow(() -> new RuntimeException("Inventory Not Found!"));
    }

    public Double reduceStocks(OrdersRequestDto ordersRequestDto)
    {
        double totalPrice = 0.0;

        for(OrdersRequestItemsDto ordersRequestItemsDto : ordersRequestDto.getItems())
        {
            Long productId = ordersRequestItemsDto.getProductId();
            Integer quantity = ordersRequestItemsDto.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new RuntimeException("Product not found!"));
            if(quantity > product.getQuantity())
            {
                throw new RuntimeException("Quantity cannot be served");
            }
            product.setQuantity(product.getQuantity()-quantity);
            productRepository.save(product);
            totalPrice += product.getPrice()*quantity;
        }
        return totalPrice;
    }
}
