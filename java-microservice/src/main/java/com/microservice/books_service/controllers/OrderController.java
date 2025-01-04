package com.microservice.books_service.controllers;

import com.microservice.books_service.dto.OrderRequest;
import com.microservice.books_service.dto.OrderResponse;
import com.microservice.books_service.dto.Token;
import com.microservice.books_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Received request to create order: {}", orderRequest);
        orderService.createOrder(orderRequest);
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getMyOrders(@RequestBody Token token) {
        return orderService.getOrdersByClientId(token.getId());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();
    }
}
