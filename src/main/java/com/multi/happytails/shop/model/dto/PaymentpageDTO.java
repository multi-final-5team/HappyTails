package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentpageDTO {
    private int id;
    private String username;
    private String productname;
    private String productinfo;
    private Timestamp date_created;
    private int productprice;
    private String imPortId;
    private int GoodsNo;

}