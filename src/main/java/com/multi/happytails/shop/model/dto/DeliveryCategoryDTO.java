package com.multi.happytails.shop.model.dto;

import lombok.*;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : DeliveryCategoryDTO.java
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
public class DeliveryCategoryDTO {

    private String deliveryNo;
    private String deliveryStatus;
}
