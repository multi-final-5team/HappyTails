package com.multi.happytails.api.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.multi.happytails.api.payment
 * fileName       : ApiKeys
 * author         : ShinHyeoncheol
 * date           : 2024-08-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-07        ShinHyeoncheol       최초 생성
 */
@Component
public class ApiKeys {
    @Value("${spring.iamport.api-key}")
    private String iamportApiKey;

    @Value("${spring.iamport.api-secret}")
    private String iamportApiSecret;

    public String getIamportApiKey() {
        return iamportApiKey;
    }

    public String getIamportApiSecret() {
        return iamportApiSecret;
    }
}