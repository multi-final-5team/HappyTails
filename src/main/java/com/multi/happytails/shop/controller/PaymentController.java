package com.multi.happytails.shop.controller;

import com.multi.happytails.api.payment.ApiKeys;
import com.multi.happytails.member.model.dao.MemberDAO;
import com.multi.happytails.member.model.dto.MemberDTO;
import com.multi.happytails.member.service.MemberService;
import com.multi.happytails.shop.model.dto.*;
import com.multi.happytails.shop.service.CartService;
import com.multi.happytails.shop.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Payment controller.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final CartService cartService;
    private final PaymentService paymentService;
    private final MemberDAO memberDAO;
    private final MemberService memberService;
    private final ApiKeys apiKeys;


    /**
     * Instantiates a new Payment controller.
     *
     * @param paymentService the payment service
     * @param memberDAO      the member dao
     * @param memberService  the member service
     * @param cartService    the cart service
     */
    public PaymentController(PaymentService paymentService, MemberDAO memberDAO, MemberService memberService, CartService cartService, ApiKeys apiKeys) {
        this.paymentService = paymentService;
        this.memberDAO = memberDAO;
        this.memberService = memberService;
        this.cartService = cartService;
        this.apiKeys = apiKeys;
    }


    /**
     * methodName : orderList
     * author : Shin HyeonCheol
     * description :
     *
     * @param model     the model
     * @param principal the principal
     * @return the string
     */
    @GetMapping("/orderlist")
    public String orderList(Model model, Principal principal) {

        List<OrderlistDTO> orderList = paymentService.selectOrders(principal.getName());
        model.addAttribute("orderList", orderList);
        return "/payment/orderlist";
    }


    /**
     * methodName : selectpaymentpage
     * author : Shin HyeonCheol
     * description :
     *
     * @param model     the model
     * @param principal the principal
     * @return the string
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

    /**
     * methodName : orderHistory
     * author : Shin HyeonCheol
     * description :
     *
     * @param model     the model
     * @param principal the principal
     * @return the string
     */
    @RequestMapping("/paymentHistory")
    public String orderHistory(Model model, Principal principal) {
        String username = principal.getName();
        List<Payment> paymentHistory = paymentService.paymentList(username);
        model.addAttribute("paymentHistory", paymentHistory);

        return "/payment/PaymentHistory";
    }


    /**
     * methodName : verifyPayment
     * author : Shin HyeonCheol
     * description :
     *
     * @param request   the request
     * @param principal the principal
     * @return the response entity
     */
    @Transactional
    @PostMapping("/verifyPayment")
    public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody VerificationRequestDTO request, Principal principal) {
        try {
            String username = principal.getName();
            List<CartDTO> cartItems = cartService.cartList(username);
            // Verify payment
            IamportClient client = new IamportClient(apiKeys.getIamportApiKey(), apiKeys.getIamportApiSecret());
            IamportResponse<Payment> paymentResponse = client.paymentByImpUid(request.getImPortId());
            BigDecimal actualAmount = paymentResponse.getResponse().getAmount();
            BigDecimal requestedAmount = BigDecimal.valueOf(cartItems.stream().mapToInt(CartDTO::totalPrice).sum());

            if (actualAmount.compareTo(requestedAmount) == 0) {
                // Create individual payment records for each item
                for (CartDTO cartItem : cartItems) {
                    PaymentDTO paymentDTO = new PaymentDTO();
                    paymentDTO.setUsername(username);
                    paymentDTO.setImPortId(request.getImPortId());
                    paymentDTO.setPurchaseprice(cartItem.totalPrice());
                    paymentDTO.setProductname(cartItem.getGoodsName());
                    paymentDTO.setProductinfo(cartItem.getGoodsName() + " x " + cartItem.getPurchaseQuantity());

                    paymentService.insertPayment(paymentDTO);
                }

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
     * methodName : cancelPayment
     * author : Shin HyeonCheol
     * description :
     *
     * @param cancelRequest the cancel request
     * @return the response entity
     */
    @PostMapping("/cancel")
    public ResponseEntity<Map<String, Object>> cancelPayment(@RequestBody VerificationRequestDTO cancelRequest) {
        try {
            if (cancelRequest.getPaymentNo() == 0 || cancelRequest.getImPortId() == null || cancelRequest.getImPortId().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "취소 실패: 유효한 paymentNo 또는 imPortId 값이 없습니다."));
            }

            BigDecimal cancelAmount = new BigDecimal(cancelRequest.getAmount().toString());

            IamportResponse<Payment> response = paymentService.cancelPayment(
                    cancelRequest.getPaymentNo(),
                    cancelRequest.getImPortId(),
                    cancelAmount,
                    "고객 요청으로 인한 취소"
            );

            paymentService.updateRefundStatus(cancelRequest.getPaymentNo());

            return ResponseEntity.ok(Map.of("success", true, "message", "결제가 성공적으로 취소되었습니다.", "data", response));
        } catch (IamportResponseException | IOException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "취소 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/partialCancel")
    public ResponseEntity<Map<String, Object>> partialCancelPayment(@RequestBody PartialCancelRequestDTO cancelRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (cancelRequest.getImPortId() == null || cancelRequest.getImPortId().isEmpty()) {
                throw new IllegalArgumentException("취소 실패: 유효한 imPortId 값이 없습니다.");
            }

            IamportResponse<Payment> paymentResponse = paymentService.partialCancelPayment(
                    cancelRequest.getPaymentNo(),
                    cancelRequest.getImPortId(),
                    cancelRequest.getProductname(),
                    cancelRequest.getPrice(),
                    cancelRequest.getQuantity(),
                    "고객 요청으로 인한 부분 취소"
            );

            response.put("success", true);
            response.put("message", "부분 결제가 성공적으로 취소되었습니다.");
            response.put("data", paymentResponse);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "부분 취소 중 오류 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @RequestMapping("/paymentUpdateDelivery")
    public String paymentUpdateDelivery(@RequestParam("payment_no") int payment_no) {

        paymentService.updateDelivery(payment_no);

        return "/payment/PaymentHistory";
    }

    @PostMapping("/updateDeliveryCode")
    @ResponseBody
    public String updateDeliveryCode(@RequestParam("paymentNo") int paymentNo,
                                     @RequestParam("deliveryCode") String deliveryCode) {
        paymentService.deliveryState(paymentNo, deliveryCode);
        return "Success";
    }

    @GetMapping("/deliveryStatusPopup")
    public String showDeliveryStatusPopup(@RequestParam("paymentNo") Long paymentNo,
                                          @RequestParam("deliveryCode") String deliveryCode,
                                          Model model) {
        model.addAttribute("paymentNo", paymentNo);
        model.addAttribute("deliveryCode", deliveryCode);
        return "/payment/deliveryStatusPopup"; // 팝업 창 HTML 파일 이름
    }
}
