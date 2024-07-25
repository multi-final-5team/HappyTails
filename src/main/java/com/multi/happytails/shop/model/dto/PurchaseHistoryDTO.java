package com.multi.happytails.shop.model.dto;

import lombok.*;

import java.sql.Timestamp;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : PurchaseHistoryDTO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 구매 내역 조회 DTO
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
public class PurchaseHistoryDTO {

    private int no;
    private String id;
    private String address;
    private String requirements;
    private int goodsNo;
    private int quantity;
    private Timestamp purchaseDate;
}
