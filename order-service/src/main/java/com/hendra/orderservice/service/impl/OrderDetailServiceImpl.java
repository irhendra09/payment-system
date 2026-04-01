package com.hendra.orderservice.service.impl;

import com.hendra.orderservice.model.OrderDetail;
import com.hendra.orderservice.repository.OrderDetailRepository;
import com.hendra.orderservice.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderDetail createOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
