package com.multi.happytails.shop.service;

import com.multi.happytails.api.payment.ApiKeys;
import com.multi.happytails.member.model.dao.MemberDAO;
import com.multi.happytails.shop.model.dao.PaymentDAO;
import com.multi.happytails.shop.model.dto.OrderlistDTO;
import com.multi.happytails.shop.model.dto.PaymentDTO;
import com.multi.happytails.shop.model.dto.PaymentpageDTO;
import com.multi.happytails.shop.model.dto.VerificationRequestDTO;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    private final ApiKeys apiKeys;
    private final MemberDAO memberDAO;

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    public PaymentService(ApiKeys apiKeys, MemberDAO memberDAO) {
        this.apiKeys = apiKeys;
        this.memberDAO = memberDAO;
    }

    public PaymentpageDTO selectpaymentpage(String username) {
        System.out.println(username + "----------------------------S");
        return paymentDAO.selectpaymentpage(username);
    }

    public int insertPayment(PaymentDTO paymentDTO) {
        return paymentDAO.insertPayment(paymentDTO);
    }

    public List<OrderlistDTO> selectOrders(String memberName) {
        return paymentDAO.selectOrders(memberName);
    }

    public int updateRefundStatus(int paymentNo) {
        return paymentDAO.updateRefundStatus(paymentNo);
    }

    public boolean verifyPayment(VerificationRequestDTO request, PaymentDTO paymentDTO) throws IamportResponseException, IOException {
        IamportClient client = new IamportClient(apiKeys.getIamportApiKey(), apiKeys.getIamportApiSecret());
        IamportResponse<Payment> payment = client.paymentByImpUid(request.getImPortId());

        BigDecimal actualAmount = payment.getResponse().getAmount();
        BigDecimal requestedAmount = BigDecimal.valueOf(paymentDTO.getPurchaseprice());

        System.out.println("Actual Amount: " + actualAmount);
        System.out.println("Requested Amount: " + requestedAmount);
        System.out.println("Comparison result: " + actualAmount.compareTo(requestedAmount));

        if (actualAmount.compareTo(requestedAmount) == 0) {
            insertPayment(paymentDTO);
            return true;
        }
        return false;
    }

    public IamportResponse<Payment> cancelPayment(int paymentNo, String imPortId, BigDecimal requestAmount, String reason)
            throws IamportResponseException, IOException {
        IamportClient client = new IamportClient(apiKeys.getIamportApiKey(), apiKeys.getIamportApiSecret());

        // 먼저 결제 정보를 조회하여 취소 가능 금액 확인
        IamportResponse<Payment> paymentResponse = client.paymentByImpUid(imPortId);
        BigDecimal paidAmount = paymentResponse.getResponse().getAmount();
        BigDecimal canceledAmount = paymentResponse.getResponse().getCancelAmount();
        BigDecimal cancelableAmount = paidAmount.subtract(canceledAmount);

        if (cancelableAmount.compareTo(requestAmount) < 0) {
            throw new IllegalArgumentException("취소 요청 금액이 취소 가능 금액보다 큽니다.");
        }

        // paymentNo를 이용하여 해당 주문의 정보를 데이터베이스에서 확인
        PaymentDTO payment = paymentDAO.getPaymentByNo(paymentNo);
        if (payment == null || !payment.getImPortId().equals(imPortId)) {
            throw new IllegalArgumentException("유효하지 않은 주문 정보입니다.");
        }

        CancelData cancelData = new CancelData(imPortId, true, requestAmount);
        cancelData.setReason(reason);

        IamportResponse<Payment> response = client.cancelPaymentByImpUid(cancelData);

        // 취소 성공 시 DB 업데이트
        if (response.getResponse().getCancelAmount().compareTo(BigDecimal.ZERO) > 0) {
            if (response.getResponse().getCancelAmount().compareTo(paidAmount) == 0) {
                // 전체 취소
                paymentDAO.updateRefundStatus(paymentNo);
            } else {
                // 부분 취소
                paymentDAO.updatePartialRefundStatus(paymentNo);
            }
        }

        return response;
    }

    public List<Payment> paymentList(String username) {
        return paymentDAO.paymentList(username);
    }

    @Transactional
    public IamportResponse<Payment> partialCancelPayment(int paymentNo, String imPortId, String productname, int price, int quantity, String reason)
            throws IamportResponseException, IOException {
        int cancelAmount = price * quantity;

        if (cancelAmount <= 0) {
            throw new IllegalArgumentException("취소 금액은 0보다 커야 합니다.");
        }

        IamportClient client = new IamportClient(apiKeys.getIamportApiKey(), apiKeys.getIamportApiSecret());

        // 결제 정보를 조회하여 취소 가능 여부 확인
        IamportResponse<Payment> paymentResponse = client.paymentByImpUid(imPortId);
        int paidAmount = paymentResponse.getResponse().getAmount().intValue();
        int canceledAmount = paymentResponse.getResponse().getCancelAmount().intValue();
        int cancelableAmount = paidAmount - canceledAmount;

        if (cancelableAmount < cancelAmount) {
            throw new IllegalArgumentException("취소 요청 금액이 취소 가능 금액보다 큽니다.");
        }

        CancelData cancelData = new CancelData(imPortId, false, BigDecimal.valueOf(cancelAmount));
        cancelData.setChecksum(BigDecimal.valueOf(cancelAmount));
        cancelData.setReason(reason);

        // 포트원 API를 통한 실제 결제 취소
        IamportResponse<Payment> cancelResponse = client.cancelPaymentByImpUid(cancelData);

        // 취소가 성공한 경우에만 DB 업데이트
        if (cancelResponse.getResponse().getStatus().equals("cancelled")) {
            int updatedRows = paymentDAO.updatePartialRefundStatus(paymentNo);
            if (updatedRows == 0) {
                throw new RuntimeException("DB 업데이트 실패: 해당 payment_no에 대한 레코드가 없거나 이미 취소되었습니다.");
            }
        } else {
            throw new RuntimeException("포트원 API를 통한 결제 취소 실패");
        }

        return cancelResponse;
    }


    public void updateDelivery(int paymentNo) {
        paymentDAO.updateDelivery(paymentNo);
    }

    public void deliveryState(int paymentNo, String deliveryCode) {
        paymentDAO.deliveryState(paymentNo, deliveryCode);
    }

    public void insertDelivery(PaymentDTO paymentDTO) {
        paymentDAO.insertDelivery(paymentDTO);
    }

    public void stateSuccess(int payment_no) {
        paymentDAO.stateSuccess(payment_no);
    }

    public List<PaymentDTO> paymentHistoryDetails(String username, String imPortId) {
        return paymentDAO.paymentHistoryDetails(username, imPortId);
    }

    public void paymentPurchaseDelivery(int paymentNo) {
        paymentDAO.paymentPurchaseDelivery(paymentNo);
    }
}
