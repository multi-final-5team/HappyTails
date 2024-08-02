package com.multi.happytails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * packageName    : com.multi.happytails.config
 * fileName       : RestTemplateConfig
 * author         : ShinHyeoncheol
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ShinHyeoncheol       최초 생성
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
