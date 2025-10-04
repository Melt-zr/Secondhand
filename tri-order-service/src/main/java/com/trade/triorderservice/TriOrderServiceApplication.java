package com.trade.triorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.trade.triorderservice", "com.tri.common"})
public class TriOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TriOrderServiceApplication.class, args);
    }

}
