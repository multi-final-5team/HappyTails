package com.multi.happytails.shop.model.dto;

import lombok.*;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : SalesCategoryDTO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 카테고리 DTO
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
public class SalesCategoryDTO {

    private String categoryCode;
    private String salesStatus;
}
