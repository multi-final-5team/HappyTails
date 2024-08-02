package com.multi.happytails.shop.controller;

import com.multi.happytails.shop.model.dto.PaymentCompleteRequest;
import com.multi.happytails.shop.model.dto.PortonePaymentResponse;
import com.multi.happytails.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * packageName    : com.multi.happytails.shop.controller
 * fileName       : PurchaseController.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
//P2Z4cX3LE4GpectU2WJb9FBwVS3imAnxydy9eqvblMmZLW3KeBqFusZGL9QyGDb3FWNqvQsMovYrWQok
@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @GetMapping("/purchase")
    public void purchase() {
        // 결제 관련 로직을 여기에 추가
    }

    @RequestMapping("/purchase-success")
    public void purchaseSuccess(){

    }

//    @RequestMapping(value="callback", method= RequestMethod.POST)
//    public ResponseEntity<?> callback_receive(@RequestBody Portone entity){
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
//        JSONObject responseObj = new JSONObject();
//
//        try {
//            String txId = entity.getTxId();
//            String paymentId = entity.getPaymentId();
//            String code = entity.getCode();
//            String message = entity.getMessage();
//
//            System.out.println("---after payment receive---");
//            System.out.println("---------------------------");
//            System.out.println("txId : " + txId);
//            System.out.println("paymentId : " + paymentId);
//            System.out.println("error_code : " + code);
//            System.out.println("error_message : " + message);
//            //결제조회 API를 통해 가ㅏ맹점이 의도한 금액과 결과금액이 맞는지 검증하는 로직을 구현합니다.
//            String status = doResult(entity);
//            responseObj.put("status", status);
//        } catch (Exception e) {
//            e.printStackTrace();
//            responseObj.put("status", "처리실패 : 관리자에게 문의해 주세요.");
//        }
//        return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
//    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    private static final String PORTONE_API_URL = "https://api.portone.io/v2/payments/";

    @PostMapping("/complete")
    public String completePayment(@RequestBody PaymentCompleteRequest request,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        // 1. 포트원 API를 통해 결제 정보 확인
        String portoneUrl = PORTONE_API_URL + request.getTxId();
        PortonePaymentResponse paymentInfo = restTemplate.getForObject(portoneUrl, PortonePaymentResponse.class);

        if (paymentInfo != null) {
            // 2. 결제 금액 검증
            if (paymentInfo.getAmount().equals(orderService.getOrderAmount(request.getPaymentId()))) {
                // 3. 주문 상태 업데이트
                boolean updateResult = orderService.updateOrderStatus(request.getPaymentId(), "PAID");

                if (updateResult) {
                    // 4. 결제 성공 처리
                    redirectAttributes.addFlashAttribute("message", "결제가 성공적으로 완료되었습니다.");
                    return "redirect:/purchase/purchase-success";
                } else {
                    // 주문 상태 업데이트 실패
                    model.addAttribute("error", "주문 상태 업데이트에 실패했습니다.");
                    return "/error/purchase-error";
                }
            } else {
                // 결제 금액 불일치
                model.addAttribute("error", "결제 금액이 일치하지 않습니다.");
                return "/error/purchase-error";
            }
        } else {
            // 포트원 API 호출 실패
            model.addAttribute("error", "결제 정보 확인에 실패했습니다.");
            return "/error/purchase-error";
        }
    }
}
