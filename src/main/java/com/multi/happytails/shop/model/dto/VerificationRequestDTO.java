package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerificationRequestDTO {
    private String imPortId;
    private BigDecimal amount;
}