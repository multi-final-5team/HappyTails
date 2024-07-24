package com.multi.happytails.shop.model.dto;

import lombok.*;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : CartDTO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
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
public class CartDTO {

    private int no;
    private String id;
    private int goodsNo;
    private int purchaseQuantity;
}
