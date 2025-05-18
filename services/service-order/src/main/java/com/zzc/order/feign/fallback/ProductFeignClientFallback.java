package com.zzc.order.feign.fallback;

import com.zzc.order.feign.ProductFeignClient;
import com.zzc.product.bean.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public Product getProductById(Long id) {
        System.out.println("兜底回调");
        Product product = new Product();
        product.setId(id);
        product.setProductName("商品不存在");
        product.setNum(0);
        product.setPrice(new BigDecimal(0));
        return product;
    }
}
