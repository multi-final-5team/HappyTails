package com.multi.happytails.shop.model.dto;

import lombok.Data;

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
    private int price;
    private int quantity;
    private int GoodsNo;
    private String address;
    private String request;
}