package com.multi.happytails.shop.controller;

import com.multi.happytails.authentication.model.dto.CustomUser;
import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import com.multi.happytails.shop.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sales")
public class SalesController {
    //글 목록, 글작성, 글수정, 상세, 리뷰
    @Autowired
    private SalesService salesservice;

    @PostMapping("/insertGoods")
    public String insertGoods (@AuthenticationPrincipal CustomUser customUser,
                               @RequestParam("name") String name,
                               @RequestParam("price") int price,
                               @RequestParam("quantity") int quantity,
                               @RequestParam("quantity") String content,
                               @RequestParam("categoryCode") String categoryCode
                               ) {
        SalesGoodsDTO salesGoodsDTO = new SalesGoodsDTO();

        salesGoodsDTO.setId(customUser.getId());
        //adress = 사업자 주소
        salesGoodsDTO.setName(name);
        salesGoodsDTO.setPrice(price);
        salesGoodsDTO.setQuantity(quantity);
        //이미지 저장
        salesGoodsDTO.setContent(content);
        salesGoodsDTO.setCategoryCode(categoryCode);

        salesservice.insertSales(salesGoodsDTO);

        return "redirect:/shop/salesList";
    }
}
