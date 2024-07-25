package com.multi.happytails.shop.model.dto;

import lombok.*;

import java.sql.Timestamp;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : SalesGoodsDTO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 정보 DTO
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
public class SalesGoodsDTO {

    private int no;
    private String id;
    private String address;
    private String name;
    private int price;
    private int quantity;
    private String content;
    private String categoryCode;
    private Timestamp createDate;
    private Timestamp updateDate;

}

