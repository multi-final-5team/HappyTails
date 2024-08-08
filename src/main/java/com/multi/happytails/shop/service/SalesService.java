package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dto.SalesGoodsDTO;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : SalesService.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 판매 관리 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
public interface SalesService {

    /**
     * methodName : insertSales
     * author : Shin HyeonCheol
     * description : 상품 판매 등록 Service
     *
     * @param salesGoodsDTO the sales goods dto
     */
    void insertSales(SalesGoodsDTO salesGoodsDTO);

    /**
     * methodName : updateSales
     * author : Shin HyeonCheol
     * description : 상품 판매 수정 Service
     *
     * @param salesGoodsDTO the sales goods dto
     * @return
     */
    int updateSales(SalesGoodsDTO salesGoodsDTO);

    /**
     * methodName : salesList
     * author : Shin HyeonCheol
     * description : 상품 판매 목록 호출 Service
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the list
     */
    List<SalesGoodsDTO> salesList(int page, int pageSize);

    /**
     * methodName : salesListBusiness
     * author : Shin HyeonCheol
     * description : 상품 판매 목록 호출 Service - 관리자
     *
     * @param page     the page
     * @param pageSize the page size
     * @param id       the id
     * @return the list
     */

    List<SalesGoodsDTO> salesListBusiness(int page, int pageSize, String id);

    /**
     * methodName : deleteSales
     * author : Shin HyeonCheol
     * description : 상품 판매 삭제 Service
     *
     * @param salesGoodsDTO the sales goods dto
     */
    void deleteSales(SalesGoodsDTO salesGoodsDTO);

    /**
     * methodName : selectSales
     * author : Shin HyeonCheol
     * description : 상품 상세 정보 호출 Service
     *
     * @param salesGoodsDTO the sales goods dto
     * @return the sales goods dto
     */
    SalesGoodsDTO selectSales(SalesGoodsDTO salesGoodsDTO);

    /**
     * Gets sales no.
     *
     * @return the sales no
     */
    int getSalesNo();

    /**
     * methodName : salesPageCount
     * author : Shin HyeonCheol
     * description : 상상품 목록 페이징 처리 Service
     *
     * @return the int
     */
    int salesPageCount();

    List<SalesGoodsDTO> search(String keyword);
}
