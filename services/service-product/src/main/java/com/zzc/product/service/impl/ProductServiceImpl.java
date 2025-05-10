package com.zzc.product.service.impl;

import com.zzc.product.bean.Product;
import com.zzc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal(1000));
        product.setProductName("huawei-"+productId);
        product.setNum(2);

        return product;
    }

}
