package com.zzc.order.service.Impl;

import com.zzc.order.bean.Order;
import com.zzc.order.service.OrderService;
import com.zzc.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order createOrder(Long productId, Long userId) {
        Product product = getProductFromRemote(productId);
        Order  order = new Order();
        order.setId(1L);
        order.setUserId(userId);
        // TODD 总金额
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setNickName("张三");
        order.setAddress("北京");
        // TODD 远程查询商品列表
        order.setProductList(Arrays.asList(product));
        return order;
    }

    private Product getProductFromRemote(Long productId){
        //1.获取商品服务所在的所有机器IP+port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");

        ServiceInstance instance = instances.get(0);
        // 远程URL
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        log.info("远程请求：{}",url);
        //2.给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }


}
