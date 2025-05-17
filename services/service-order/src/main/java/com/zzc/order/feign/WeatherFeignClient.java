package com.zzc.order.feign;

import com.zzc.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-weather",url="https://aliv18.data.moji.com",configuration = FeignConfig.class)
public interface WeatherFeignClient {

    @PostMapping("/whapi/json/alicityweather/condition")
    String getWeather(@RequestHeader("Authorization") String authorization,
                    @RequestParam("token") String token,
                    @RequestParam("cityId") String cityId);
}
