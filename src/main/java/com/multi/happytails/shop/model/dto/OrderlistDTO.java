package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderlistDTO {
    private String username;
    private String productname;
    private String productinfo;
    private Timestamp date_created;
    private int purchaseprice;
    private String imPortId;

    private String refund;
    private String refund_date;

}
