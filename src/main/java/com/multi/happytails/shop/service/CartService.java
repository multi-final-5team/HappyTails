package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dto.CartDTO;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : CartService
 * author         : ShinHyeoncheol
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        ShinHyeoncheol       최초 생성
 */
public interface CartService {
    void insertCart(CartDTO cartDTO);

    List<CartDTO> cartList(String id);
}
