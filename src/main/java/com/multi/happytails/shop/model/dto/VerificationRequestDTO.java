package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerificationRequestDTO {
    private int paymentNo;
    private String imPortId;
    private BigDecimal amount;
    private int GoodsNo;
    private String email;
    private String address;
    private String request;
    private String seller;
}
