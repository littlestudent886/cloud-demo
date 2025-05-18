package com.zzc.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class XTokenRequestInterceptor implements RequestInterceptor {

    /*
    * 请求拦截器
    * @ param template 请求模板
    * 对远程请求进行封装
    * */
    @Override
    public void apply(RequestTemplate template) {
        System.out.println("拦截器启动~~~~");
        template.header("X-Token", UUID.randomUUID().toString());
    }
}
