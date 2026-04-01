package com.hendra.orderservice.service;

import com.hendra.orderservice.dto.OrderRequest;
import com.hendra.orderservice.dto.OrderResponse;
import com.hendra.orderservice.model.Order;

public interface OrderService {
    Order createOrder(OrderRequest request);
}
