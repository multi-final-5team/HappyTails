package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dto.SalesCategoryDTO;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : SalesCategoryService.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
public interface SalesCategoryService {
    List<SalesCategoryDTO> categoryList();
}
