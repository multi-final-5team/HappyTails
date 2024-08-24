package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.CartDTO;
import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import com.multi.happytails.shop.service.CartService;
import com.multi.happytails.shop.service.SalesService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private SalesService salesService;

    @PostMapping("/insertCart")
    @ResponseBody
    public int insertCart(Principal principal,
                               @RequestParam("goodsNo") int goodsNo,
                               @RequestParam("purchaseQuantity") int purchaseQuantity
                                ) {
        int result = 0;
        CartDTO cartDTO = new CartDTO();
        String id = principal.getName();
        SalesGoodsDTO salesGoodsDTO = new SalesGoodsDTO();
        salesGoodsDTO.setNo(goodsNo);

        SalesGoodsDTO salesDetails = salesService.selectSales(salesGoodsDTO);
        if(salesDetails.getQuantity() < purchaseQuantity){
            return 0;
        } else {
            result = (salesDetails.getQuantity() - purchaseQuantity);
            salesService.updateQuantity(result, goodsNo);


            cartDTO.setId(id);
            cartDTO.setGoodsNo(goodsNo);

            cartDTO.setPurchaseQuantity(purchaseQuantity);
            cartDTO.setSeller(salesDetails.getId());

            cartService.insertCart(cartDTO);

            return 1; // adjust the redirect as necessary
        }
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



    @GetMapping("/deleteCart")
    public String deleteCart (@RequestParam("no") int no, HttpServletRequest request){
        int result = 0;
        CartDTO cartInfo = cartService.getInfoByNo(no);
        SalesGoodsDTO salesGoodsDTO = new SalesGoodsDTO();
        salesGoodsDTO.setNo(cartInfo.getGoodsNo());
        SalesGoodsDTO salesInfo = salesService.selectSales(salesGoodsDTO);
        result = (salesInfo.getQuantity() + cartInfo.getPurchaseQuantity());
        salesService.updateQuantity(result, cartInfo.getGoodsNo());
        cartService.deleteCart(no);

        String referer = request.getHeader("Referer"); // 이전 페이지 URL 가져오기
        return "redirect:" + referer;
    }
}
