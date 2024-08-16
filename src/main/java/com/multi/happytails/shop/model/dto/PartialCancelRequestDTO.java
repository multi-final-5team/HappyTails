package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : PartialCancelRequestDTO
 * author         : ShinHyeoncheol
 * date           : 2024-08-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-16        ShinHyeoncheol       최초 생성
 */
@Data
public class PartialCancelRequestDTO {
    private int paymentNo;
    private String imPortId;
    private String productname;
    private BigDecimal price;
    private int quantity;
}