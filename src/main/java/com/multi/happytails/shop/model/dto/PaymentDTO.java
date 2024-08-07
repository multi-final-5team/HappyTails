package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentDTO {
    private int payment_no;
    private String username;
    private String productname;
    private String productinfo;
    private int productprice;
    private int purchaseprice;
    private Timestamp date_created;
    private String imPortId;


}
