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
        BigDecimal requestedAmount = request.getAmount();

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

}