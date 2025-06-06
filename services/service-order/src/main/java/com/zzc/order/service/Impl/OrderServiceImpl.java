package com.zzc.order.service.Impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zzc.order.bean.Order;
import com.zzc.order.feign.ProductFeignClient;
import com.zzc.order.service.OrderService;
import com.zzc.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    ProductFeignClient productFeignClient;


    @SentinelResource(value = "createOrder",blockHandler = "createOrderFallback")
    @Override
    public Order createOrder(Long productId, Long userId) {
//        Product product = getProductFromRemoteWithLoadBalancerAnnotation(productId);
        Product product = productFeignClient.getProductById(productId);
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

    // 兜底回调
    public Order createOrderFallback(Long productId, Long userId, BlockException e){
        Order order = new Order();
        order.setId(0L);
        order.setUserId(userId);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setNickName("未知用户");
        order.setAddress("异常信息"+e.getClass());
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

    // 进阶：完成负载均衡发送请求
    private Product getProductFromRemoteWithLoadBalancer(Long productId){
        //1.获取商品服务所在的所有机器IP+port
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        // 远程URL
        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/" + productId;
        log.info("远程请求：{}",url);
        //2.给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    // 进阶2：基于注解的负载均衡
     private Product getProductFromRemoteWithLoadBalancerAnnotation(Long productId){
         String url = "http://service-product/product/" + productId;
         // 给远程发送请求时，service-product会被动态替换成ip：port
         Product product = restTemplate.getForObject(url, Product.class);
         return product;
     }

}
