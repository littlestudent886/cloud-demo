package com.zzc.order;

import com.zzc.order.feign.WeatherFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherTest {
    @Autowired
    WeatherFeignClient  weatherFeignClient;

    @Test
    public void testWeather(){
        String weather = weatherFeignClient.getWeather("APPCODE 93b7e19861a24c519a7548b17dc16d75", "50b53ff8dd7d9fa320d3d3ca32cf8ed1", "210");
        System.out.println(weather);
    }
}
