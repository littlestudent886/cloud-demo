package com.zzc.order.controller;

import com.zzc.order.bean.Order;
import com.zzc.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    // 创建订单
    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId) {
        Order order = orderService.createOrder(productId, userId);
        return order;
    }
}
