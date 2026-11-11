package com.eCommerce.orders_service.service;

import com.eCommerce.orders_service.configs.InventoryFeignClients;
import com.eCommerce.orders_service.dtos.OrdersRequestDto;
import com.eCommerce.orders_service.entities.Orders;
import com.eCommerce.orders_service.entities.OrdersItem;
import com.eCommerce.orders_service.enums.OrderStatus;
import com.eCommerce.orders_service.repository.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClients inventoryFeignClients;
    public List<OrdersRequestDto> getAllOrders()
    {
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream()
                .map(orders1 -> modelMapper.map(orders1,OrdersRequestDto.class))
                .toList();
    }

    public OrdersRequestDto getOrdersById(Long id)
    {
        Optional<Orders> ordersRequestDto = ordersRepository.findById(id);
        return ordersRequestDto
                .map(orders -> modelMapper.map(orders, OrdersRequestDto.class))
                .orElseThrow(()-> new RuntimeException("Orders Not Found!"));
    }


   // @Retry(name = "inventoryRetry",fallbackMethod = "createOrdersFallBack")
   // @RateLimiter(name = "inventoryRateLimiter",fallbackMethod = "createOrdersFallBack")
    @CircuitBreaker(name = "inventoryCircuitBreaker",fallbackMethod = "createOrdersFallBack")
    public OrdersRequestDto createOrders(OrdersRequestDto ordersRequestDto) {
        log.info("Calling createOrders method");
        Double totalPrice = inventoryFeignClients.reduceStocks(ordersRequestDto);
        Orders order = modelMapper.map(ordersRequestDto,Orders.class);
        for(OrdersItem item : order.getItems())
        {
            item.setOrders(order);
        }
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalPrice(totalPrice);
        Orders savedOrders=ordersRepository.save(order);
        return modelMapper.map(savedOrders,OrdersRequestDto.class);

    }

    public OrdersRequestDto createOrdersFallBack(OrdersRequestDto ordersRequestDto,Throwable throwable) {
        log.error(throwable.toString());
        return new OrdersRequestDto();

    }

}
