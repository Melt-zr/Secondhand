package com.tri.common.config;


import com.tri.common.properties.AliProperties;
import com.tri.common.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliProperties aliProperties) {
        return new AliOssUtil(aliProperties.getEndpoint(),
                    aliProperties.getAccessKeyId(),
                    aliProperties.getAccessKeySecret(),
                    aliProperties.getBucketName());
    }

}
