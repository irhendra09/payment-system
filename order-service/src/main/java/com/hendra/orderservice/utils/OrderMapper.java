package com.hendra.orderservice.utils;

import com.hendra.orderservice.dto.OrderDetailResponse;
import com.hendra.orderservice.dto.OrderResponse;
import com.hendra.orderservice.model.Order;
import com.hendra.orderservice.model.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .orderDetails(
                        order.getOrderDetails().stream()
                                .map(this::toDetailResponse)
                                .toList()
                )
                .build();
    }

    private OrderDetailResponse toDetailResponse(OrderDetail detail) {
        return OrderDetailResponse.builder()
                .productId(detail.getProductId())
                .productName(detail.getProductName())
                .quantity(detail.getQuantity())
                .price(detail.getPrice())
                .subtotal(detail.getSubtotal())
                .build();
    }
}
