package com.zzc.order.service;

import com.zzc.order.bean.Order;

public interface OrderService {
    Order createOrder(Long productId,  Long userId);
}
