package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dto.SalesGoodsDTO;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : SalesService.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
public interface SalesService {

    void insertSales(SalesGoodsDTO salesGoodsDTO);

    void updateSales(SalesGoodsDTO salesGoodsDTO);

    List<SalesGoodsDTO> salesList(int page, int pageSize);

    List<SalesGoodsDTO> salesListBusiness(int page, int pageSize, String id);

    void deleteSales(SalesGoodsDTO salesGoodsDTO);

    SalesGoodsDTO selectSales(SalesGoodsDTO salesGoodsDTO);

    int getSalesNo();

    int salesPageCount();
}
