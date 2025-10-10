package com.trade.triorderservice.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getInfo()
                    .title("订单服务接口文档")
                    .description("订单服务接口文档")
                    .version("1.0");
        };
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("order-public")
                .pathsToMatch("/order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reviewApi() {
        return GroupedOpenApi.builder()
                .group("review-public")
                .pathsToMatch("/review/**")
                .build();
    }
}
