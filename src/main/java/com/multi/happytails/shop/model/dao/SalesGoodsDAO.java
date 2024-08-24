package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.model.dao
 * fileName       : SalesGoodsDAO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 판매 관리 DAO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Mapper
public interface SalesGoodsDAO {

    /**
     * methodName : insertSales
     * author : Shin HyeonCheol
     * description : 상품 등록 DAO
     *
     * @param salesGoodsDTO the sales goods dto
     */
    void insertSales(SalesGoodsDTO salesGoodsDTO);

    /**
     * methodName : updateSales
     * author : Shin HyeonCheol
     * description : 상품 수정 DAO
     *
     * @param salesGoodsDTO the sales goods dto
     */
    int updateSales(SalesGoodsDTO salesGoodsDTO);

    /**
     * methodName : salesList
     * author : Shin HyeonCheol
     * description : 상품 목록 호출 DAO
     *
     * @param offset the offset
     * @param limit  the limit
     * @return the list
     */
    List<SalesGoodsDTO> salesList(@Param("offset") int offset, @Param("limit") int limit);
    List<SalesGoodsDTO> salesListAdmin(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * methodName : salesListBusiness
     * author : Shin HyeonCheol
     * description : 상품 목록 호출 DAO - 사업자
     *
     * @param offset the offset
     * @param limit  the limit
     * @param id     the id
     * @return the list
     */
    List<SalesGoodsDTO> salesListBusiness(@Param("offset") int offset, @Param("limit") int limit, @Param("id") String id);

    /**
     * methodName : deleteSales
     * author : Shin HyeonCheol
     * description : 상품 삭제 DAO
     *
     * @param salesGoodsDTO the sales goods dto
     */
    void deleteSales(SalesGoodsDTO salesGoodsDTO);

    /**
     * methodName : selectSales
     * author : Shin HyeonCheol
     * description : 상품 상세 정보 호출 DAO
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
     * description : 상품 목록 페이징 처리 DAO
     *
     * @return the int
     */
    int salesPageCount();

    List<SalesGoodsDTO> search(String keyword);

    List<SalesGoodsDTO> getRandomSalesGoods(int count);

    List<SalesGoodsDTO> getRandomProducts();

    int salesPageCountForUser(String id);

    void updateQuantity(@Param("quantity")int result, @Param("no") int goodsNo);
}
