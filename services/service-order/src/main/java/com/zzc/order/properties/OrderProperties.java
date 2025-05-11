package com.zzc.order.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "order") //配置批量绑定。在nacos中，可以无需@RefreshScope就能实现自动刷新
@Data
public class OrderProperties {
    String timeout;
    String autoConfirm;
}
