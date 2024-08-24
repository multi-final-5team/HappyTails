package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.CartDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.model.dao
 * fileName       : CartDAO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Mapper
public interface CartDAO {
    void insertCart(CartDTO cartDTO);

    List<CartDTO> cartList(String id);

    void clearCart(String username);

    void updateCart(@Param("purchaseQuantity") int purchaseQuantity, @Param("no") int no);

    void deleteCart(int no);

    CartDTO getInfoByNo(int no);
}
