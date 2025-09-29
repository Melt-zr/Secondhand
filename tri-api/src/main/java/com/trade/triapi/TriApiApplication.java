package com.trade.triapi;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.trade.triapi.client") // 指定Feign Client所在包
public class TriApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TriApiApplication.class, args);
    }
}
