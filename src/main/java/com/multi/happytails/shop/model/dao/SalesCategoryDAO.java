package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.SalesCategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.model.dao
 * fileName       : SalesCategoryDAO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 카테고리 DAO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Mapper
public interface SalesCategoryDAO {
    /**
     * methodName : categoryList
     * author : Shin HyeonCheol
     * description : 상품 카테고리 목록 호출 메소드
     *
     * @return the list
     */
    List<SalesCategoryDTO> categoryList();
}
