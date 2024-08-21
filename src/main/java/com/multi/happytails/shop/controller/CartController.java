package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.CartDTO;
import com.multi.happytails.shop.service.CartService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UploadService uploadService;

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
        int totalPrice = 0;
        String id = principal.getName();
        List<CartDTO> cartList = cartService.cartList(id);

        Map<Integer, List<UploadDto>> cartMap = new HashMap<>();

        for (CartDTO cartDTO : cartList) {
            int goodsNo = cartDTO.getGoodsNo();
            cartMap.put(goodsNo, uploadService.uploadSelect("S", goodsNo));
        }

        model.addAttribute("cartList", cartList);
        model.addAttribute("cartMap", cartMap);


        return "cart/cartList";
    }

    @PostMapping("/updateCart")
    @ResponseBody
    public String updateCart(@RequestParam("purchaseQuantity") int purchaseQuantity,
                             @RequestParam("no") int no) {
        System.out.println(purchaseQuantity);
        System.out.println(no);

        cartService.updateCart(purchaseQuantity, no);

        return "dd";
    }
}
