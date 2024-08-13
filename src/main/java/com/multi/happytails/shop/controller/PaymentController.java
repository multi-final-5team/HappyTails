package com.multi.happytails.shop.controller;

import com.multi.happytails.member.model.dao.MemberDAO;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.shop.model.dto.*;
import com.multi.happytails.shop.service.CartService;
import com.multi.happytails.shop.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final CartService cartService;
    private final PaymentService paymentService;
    private final MemberDAO memberDAO;
    private final MemberService memberService;

    public PaymentController(PaymentService paymentService, MemberDAO memberDAO, MemberService memberService, CartService cartService) {
        this.paymentService = paymentService;
        this.memberDAO = memberDAO;
        this.memberService = memberService;
        this.cartService = cartService;
    }

    /**
     * 주문내역페이지 구성
     *
     * @param model 뷰단으로 데이터이동
     * @return 주문내역페이지 열기
     */
    @GetMapping("/orderlist")
    public String orderList(Model model, Principal principal) {

        List<OrderlistDTO> orderList = paymentService.selectOrders(principal.getName());
        model.addAttribute("orderList", orderList);
        return "/payment/orderlist";
    }

    /**
     * 주문 및 결제 페이지
     *
     * @param model 뷰단으로 데이터 이동
     * @return 결제페이지 열기
     */
    @GetMapping("/payment")
    public String selectpaymentpage(Model model, Principal principal) {
        MemberDTO memberDTO = memberDAO.findMemberById(principal.getName());
        String username = principal.getName();
        List<CartDTO> cartList = cartService.cartList(username);

        model.addAttribute("cartList", cartList);
        model.addAttribute("memberDTO", memberDTO);

        return "/payment/payment";
    }

    @RequestMapping("/paymentHistory")
    public String orderHistory(Model model, Principal principal) {
        String username = principal.getName();
        List<Payment> paymentHistory = paymentService.paymentList(username);
        model.addAttribute("paymentHistory", paymentHistory);

        return "/payment/PaymentHistory";
    }

    @GetMapping("/payment2")
    public String selectpaymentpage2(Model model, Principal principal) {
        MemberDTO memberDTO = memberDAO.findMemberById(principal.getName());
        String username = principal.getName();
        PaymentpageDTO paymentpageDTO = paymentService.selectpaymentpage(username);
        System.out.println(memberDTO + "==========================");
        System.out.println(username + "==========================");
        System.out.println(paymentpageDTO + "==========================");
        model.addAttribute("paymentpageDTO", paymentpageDTO);
        model.addAttribute("memberDTO", memberDTO);

        return "/payment/payment";
    }

    /**
     * 결제검증 메서드
     * 결제시 사용포인트 조회 및 결제내역 저장
     * @param request 결제검증 dto
     * @return 결제 검증 결과 전송
     */
    @Transactional
    @PostMapping("/verifyPayment")
    public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody VerificationRequestDTO request, Principal principal) {
        try {
            String username = principal.getName();
            List<CartDTO> cartItems = cartService.cartList(username);

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setUsername(username);
            paymentDTO.setImPortId(request.getImPortId());

            // Calculate total price from cart items
            int totalPrice = cartItems.stream().mapToInt(CartDTO::totalPrice).sum();
            paymentDTO.setPurchaseprice(totalPrice);

            // Combine product information
            String productInfo = cartItems.stream()
                    .map(item -> item.getGoodsName() + " x " + item.getPurchaseQuantity())
                    .collect(Collectors.joining(", "));
            paymentDTO.setProductinfo(productInfo);

            // Use the first item's name as the main product name
            paymentDTO.setProductname(cartItems.get(0).getGoodsName() + (cartItems.size() > 1 ? " 외 " + (cartItems.size() - 1) + "건" : ""));

            // Verify payment
            boolean verified = paymentService.verifyPayment(request, paymentDTO);

            if (verified) {
                // Clear the cart after successful payment
                cartService.clearCart(username);
                return ResponseEntity.ok(Map.of("success", true, "message", "Payment verified successfully"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Payment amount mismatch"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Payment verification failed: " + e.getMessage()));
        }
    }

    /**
     * 결제 취소 매서드
     * 결제취소시 사용된 포인트 반환 및 실결제금액 반환
     * @param cancelRequest 결제검증dto를 통한 결제취소 요청 데이터
     * @return 결제취소에 대한 성공여부 반환
     */
    @PostMapping("/cancel")
    public ResponseEntity<Map<String, Object>> cancelPayment(@RequestBody VerificationRequestDTO cancelRequest) {
        try {
            if (cancelRequest.getImPortId() == null || cancelRequest.getImPortId().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "취소 실패: 유효한 imPortId 값이 없습니다."));
            }

            IamportResponse<Payment> response = paymentService.cancelPayment(
                    cancelRequest.getImPortId(),
                    cancelRequest.getAmount(),
                    "고객 요청으로 인한 취소"
            );

            paymentService.updateRefundStatus(cancelRequest.getImPortId());

            return ResponseEntity.ok(Map.of("success", true, "message", "결제가 성공적으로 취소되었습니다.", "data", response));
        } catch (IamportResponseException | IOException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "취소 실패: " + e.getMessage()));
        }
    }

}
