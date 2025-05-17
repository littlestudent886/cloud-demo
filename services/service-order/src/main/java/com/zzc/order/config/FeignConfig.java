package com.zzc.order.config;

import feign.Client;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class FeignConfig {
    @Bean
    public Client feignClient() throws NoSuchAlgorithmException, KeyManagementException {
        // 忽略 SSL 证书校验
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }};
        sslContext.init(null, trustAllCerts, null);

        return new Client.Default(sslContext.getSocketFactory(), (hostname, session) -> true);
    }
}
