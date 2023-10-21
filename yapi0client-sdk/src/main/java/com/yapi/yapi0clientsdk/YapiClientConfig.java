package com.yapi.yapi0clientsdk;

import com.yapi.yapi0clientsdk.client.YapiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yuuue
 * creat by 2023-08-29
 */
@Configuration
@ConfigurationProperties("yapi-client")
@Data
@ComponentScan
public class YapiClientConfig {

    private String accessKey ;

    private String secretKey;

    @Bean
    public YapiClient yapiClient() {
        return new YapiClient(accessKey,secretKey);
    }
}
