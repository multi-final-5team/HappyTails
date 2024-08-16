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

    public int updateRefundStatus(String imPortId) {
        return paymentDAO.updateRefundStatus(imPortId);
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

    public IamportResponse<Payment> cancelPayment(String imPortId, BigDecimal requestAmount, String reason)
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

        CancelData cancelData = new CancelData(imPortId, true);
        cancelData.setChecksum(requestAmount);
        cancelData.setReason(reason);

        return client.cancelPaymentByImpUid(cancelData);
    }

    public List<Payment> paymentList(String username) {
        return paymentDAO.paymentList(username);
    }

    @Transactional
    public IamportResponse<Payment> partialCancelPayment(int paymentNo, String imPortId, String productname, BigDecimal price, int quantity, String reason)
            throws IamportResponseException, IOException {
        BigDecimal cancelAmount = price.multiply(BigDecimal.valueOf(quantity));

        if (cancelAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("취소 금액은 0보다 커야 합니다.");
        }

        IamportClient client = new IamportClient(apiKeys.getIamportApiKey(), apiKeys.getIamportApiSecret());

        // 결제 정보를 조회하여 취소 가능 여부 확인
        IamportResponse<Payment> paymentResponse = client.paymentByImpUid(imPortId);
        BigDecimal paidAmount = paymentResponse.getResponse().getAmount();
        BigDecimal canceledAmount = paymentResponse.getResponse().getCancelAmount();
        BigDecimal cancelableAmount = paidAmount.subtract(canceledAmount);

        if (cancelableAmount.compareTo(cancelAmount) < 0) {
            throw new IllegalArgumentException("취소 요청 금액이 취소 가능 금액보다 큽니다.");
        }

        CancelData cancelData = new CancelData(imPortId, false, cancelAmount);
        cancelData.setChecksum(cancelAmount);
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

    public int updatePartialRefundStatus(int paymentNo) {
        return paymentDAO.updatePartialRefundStatus(paymentNo);
    }
}