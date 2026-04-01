package com.hendra.orderservice.service.impl;

import com.hendra.orderservice.dto.OrderDetailResponse;
import com.hendra.orderservice.dto.OrderItemRequest;
import com.hendra.orderservice.dto.OrderRequest;
import com.hendra.orderservice.dto.OrderResponse;
import com.hendra.orderservice.model.Order;
import com.hendra.orderservice.model.OrderDetail;
import com.hendra.orderservice.model.OrderStatus;
import com.hendra.orderservice.model.Product;
import com.hendra.orderservice.repository.OrderRepository;
import com.hendra.orderservice.repository.ProductRepository;
import com.hendra.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setStatus(OrderStatus.CREATED);

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest item : request.getItems()) {

            Product product = productRepository.findById(item.getProductId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

            OrderDetail detail = new OrderDetail();
            detail.setProductId(product.getId());
            detail.setProductName(product.getName());
            detail.setPrice(product.getPrice());
            detail.setQuantity(item.getQuantity());

            detail.setOrder(order);
            if (product.getStock() - item.getQuantity() < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceeds");
            BigDecimal quantity = new BigDecimal(item.getQuantity());
            detail.setSubtotal(product.getPrice().multiply(quantity));
            total = total.add(product.getPrice().multiply(quantity));
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
            details.add(detail);
        }

        order.setOrderDetails(details);
        order.setTotalAmount(total);
        orderRepository.saveAndFlush(order);

        return order;
    }
}
