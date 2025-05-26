package com.zzc.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zzc.order.bean.Order;
import com.zzc.order.properties.OrderProperties;
import com.zzc.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope //激活配置属性的自动刷新功能
//@RequestMapping("/api/order")
@Slf4j
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

//    @Value("${order.timeout}")
//    String orderTimeout;
//    @Value("${order.auto-confirm}")
//    String orderAutoConfirm;
    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/config")
    public String config(){
        return "order.timeout:"+orderProperties.getTimeout()+
                ",order.auto-confirm:"+orderProperties.getAutoConfirm()+
                ",order.db-url:"+orderProperties.getDbUrl();
    }

    // 创建订单
    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId) {
        Order order = orderService.createOrder(productId, userId);
        return order;
    }

    // 创建秒杀订单
    @GetMapping("/seckill")
    @SentinelResource(value = "seckill-order", fallback = "seckillFallback")
    public Order seckill(@RequestParam(value = "productId",required = false) Long productId, @RequestParam(value = "userId",required = false) Long userId) {
        Order order = orderService.createOrder(productId, userId);
        return order;
    }

    public Order seckillFallback(Long productId, Long userId, Throwable exception) {
        System.out.println("seckillFallback...");
        Order order = new Order();
        order.setId(productId);
        order.setUserId(userId);
        order.setAddress("异常信息："+exception.getClass());
        return order;
    }

    // 读数据库
    @GetMapping("/readDb")
    public String readDb(){
        System.out.println("readDb...");
        return "readDb success...";
    }

    // 写数据库
    @GetMapping("/writeDb")
    public String writeDb(){
        return "writeDb success...";
    }
}
