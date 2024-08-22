package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dao.CartDAO;
import com.multi.happytails.shop.model.dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : CartServiceImpl
 * author         : ShinHyeoncheol
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        ShinHyeoncheol       최초 생성
 */
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartDAO cartDAO;

    @Override
    public void insertCart(CartDTO cartDTO) {
        cartDAO.insertCart(cartDTO);
    }

    @Override
    public List<CartDTO> cartList(String id) {
        return cartDAO.cartList(id);
    }

    public void clearCart(String username) {
        cartDAO.clearCart(username);
    }

    @Override
    public void updateCart(int purchaseQuantity, int no) {
        cartDAO.updateCart(purchaseQuantity, no);
    }

    @Override
    public void deleteCart(int no) {
        cartDAO.deleteCart(no);
    }

}
