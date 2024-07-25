package com.multi.happytails.shop.model.dto;

import lombok.*;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : DeliveryStatusDTO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 배송 상태 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DeliveryStatusDTO {

    private int no;
    private int purchaseNo;
    private String id;
    private String statusCode;
    private int invoiceNumber;
}
