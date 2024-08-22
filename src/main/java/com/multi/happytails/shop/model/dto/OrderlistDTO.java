package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderlistDTO {
    private int payment_no;
    private String username;
    private String productname;
    private String productinfo;
    private Timestamp date_created;
    private int purchaseprice;
    private String imPortId;
    private String delivery_code;
    private int GoodsNo;
    private String delivery_man;
    private int invoice_number;
    private String address;
    private String request;
    private String seller;

    private String refund;
    private String refund_date;

}