package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dao.SalesCategoryDAO;
import com.multi.happytails.shop.model.dto.SalesCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : SalesCategoryServiceImpl.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 카테고리 ServiceImpl
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Service
public class SalesCategoryServiceImpl implements SalesCategoryService {

    @Autowired
    private SalesCategoryDAO salesCategoryDAO;


    @Override
    public List<SalesCategoryDTO> categoryList() {
        return salesCategoryDAO.categoryList();
    }
}
