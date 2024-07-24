package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.multi.happytails.shop.controller
 * fileName       : PurchaseController.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Controller
@RequestMapping("/sales")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
}
