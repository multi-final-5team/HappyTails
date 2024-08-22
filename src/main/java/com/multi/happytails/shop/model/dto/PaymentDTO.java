package com.multi.happytails.shop.model.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

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
    private String refund;
    private Timestamp refund_date;
    private String delivery_code;
    private int goodsNo;
    private String delivery_man;
    private int invoice_number;
    private String address;
    private String request;
    private String seller;

    private List<CartDTO> cartItems;

    public int calculateTotalPrice() {
        return cartItems != null ? cartItems.stream().mapToInt(CartDTO::totalPrice).sum() : 0;
    }

    public String generateProductInfo() {
        if (cartItems == null || cartItems.isEmpty()) {
            return "";
        }
        return cartItems.stream()
                .map(item -> item.getGoodsName() + " x " + item.getPurchaseQuantity())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    public String generateMainProductName() {
        if (cartItems == null || cartItems.isEmpty()) {
            return "";
        }
        return cartItems.get(0).getGoodsName() +
                (cartItems.size() > 1 ? " 외 " + (cartItems.size() - 1) + "건" : "");
    }
}
