package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.CartDTO;
import com.multi.happytails.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.controller
 * fileName       : CartController
 * author         : ShinHyeoncheol
 * date           : 2024-07-26
 * description    : 장바구니 관리 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        ShinHyeoncheol       최초 생성
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/insertCart")
    public String insertReview(Principal principal,
                               @RequestParam("goodsNo") int goodsNo,
                               @RequestParam("purchaseQuantity") int purchaseQuantity
                                ) {
        CartDTO cartDTO = new CartDTO();
        String id = principal.getName();
        cartDTO.setId(id);
        cartDTO.setGoodsNo(goodsNo);
        cartDTO.setPurchaseQuantity(purchaseQuantity);

        cartService.insertCart(cartDTO);

        return "redirect:/cart/cartList"; // adjust the redirect as necessary
    }

    @GetMapping("/cartList")
    public String cartList(Principal principal, Model model){
        String id = principal.getName();
        List<CartDTO> cartList = cartService.cartList(id);

        model.addAttribute("cartList", cartList);

        return "redirect:/cart/cartList";
    }
}
