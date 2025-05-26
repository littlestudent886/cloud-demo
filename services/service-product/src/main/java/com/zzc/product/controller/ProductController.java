package com.zzc.product.controller;

import com.zzc.product.bean.Product;
import com.zzc.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

//@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId,
                              HttpServletRequest request) throws InterruptedException {
        String header = request.getHeader("X-Token");
        Product product = productService.getProductById(productId);
        System.out.println("hello "+header);
//        TimeUnit.SECONDS.sleep(2);
//        System.out.println(10/0);
        return product;
    }
}
