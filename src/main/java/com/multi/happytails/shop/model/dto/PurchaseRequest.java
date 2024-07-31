package com.multi.happytails.shop.model.dto;

import lombok.Data;

/**
 * packageName    : com.multi.happytails.shop.model.dto
 * fileName       : PurchaseRequest
 * author         : ShinHyeoncheol
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        ShinHyeoncheol       최초 생성
 */
@Data
public class PurchaseRequest {
    private String merchantUid;
    private int amount;
    private String orderName;
    private String buyerName;
    private String buyerEmail;
}
