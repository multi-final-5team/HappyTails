package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.SalesCategoryDTO;
import com.multi.happytails.shop.service.SalesCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.controller
 * fileName       : SalesCategoryController.java
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
public class SalesCategoryController {

    @Autowired
    private SalesCategoryService salesCategoryService;

    /**
     * methodName : categoryList
     * author : Shin HyeonCheol
     * description :
     *
     * @return the list
     */
    @GetMapping("/categoryList")
    @ResponseBody
    public List<SalesCategoryDTO> categoryList() {
        return salesCategoryService.categoryList();
    }
    // List


    //PostMan

//    @GetMapping("/categoryList")
//    @ResponseBody
//    public List<SalesCategoryDTO> CategoryList() {
//        return salesCategoryService.categoryList();
//    }
    // List


}
