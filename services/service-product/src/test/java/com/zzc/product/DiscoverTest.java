package com.zzc.product;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest
public class DiscoverTest {
    @Autowired
    DiscoveryClient discoveryClient; // 所有的注册中心都可以使用

    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery; // nacos注册中心才可以使用

    @Test
    void nacosServiceDiscoveryTest() throws NacosException {
        nacosServiceDiscovery.getServices().forEach(System.out::println);
    }


    @Test
    void discoverClientTest(){
//        discoveryClient.getServices().forEach(System.out::println);
        for (String service : discoveryClient.getServices()) {
            System.out.println("discover="+service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("ip:"+instance.getHost()+" port:"+instance.getPort());
            }
        }

    }
}
