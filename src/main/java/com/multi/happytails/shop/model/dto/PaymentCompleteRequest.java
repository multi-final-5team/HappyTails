package com.multi.happytails.shop.model.dto;

import lombok.*;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : PaymentCompleteRequest
 * author         : ShinHyeoncheol
 * date           : 2024-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-02        ShinHyeoncheol       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PaymentCompleteRequest {

    private String txId;
    private String paymentId;
}
