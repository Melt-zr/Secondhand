package com.trade.triuserservice.config;

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
                    .title("用户服务接口文档")
                    .description("用户服务接口文档")
                    .version("1.0");
        };
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("user-public")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi addressApi() {
        return GroupedOpenApi.builder()
                .group("address-public")
                .pathsToMatch("/addresses/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("browsing-public")
                .pathsToMatch("/browsing-history/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("favorites-public")
                .pathsToMatch("/favorites/**")
                .build();
    }
}
