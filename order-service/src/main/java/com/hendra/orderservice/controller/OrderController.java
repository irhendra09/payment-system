package com.hendra.orderservice.controller;

import com.hendra.orderservice.dto.OrderRequest;
import com.hendra.orderservice.service.OrderService;
import com.hendra.orderservice.utils.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    @PostMapping
    public ResponseEntity<?> createNewOrder(@RequestBody OrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toResponse(orderService.createOrder(request)));
    }
}
