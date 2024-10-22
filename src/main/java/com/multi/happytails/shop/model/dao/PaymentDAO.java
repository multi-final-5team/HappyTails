package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.OrderlistDTO;
import com.multi.happytails.shop.model.dto.PaymentDTO;
import com.multi.happytails.shop.model.dto.PaymentpageDTO;
import com.siot.IamportRestClient.response.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentDAO {

    PaymentpageDTO selectpaymentpage(@Param("username") String username);

    int insertPayment(PaymentDTO paymentDTO);

    int insertPaymentItems(PaymentDTO paymentDTO);

    List<OrderlistDTO> selectOrders(String memberName);

    List<OrderlistDTO> selectOrders2(@Param("seller") String name);

    PaymentDTO getPaymentDetails(String imPortId);

    int updateRefundStatus(@Param("paymentNo") int paymentNo);

    List<Payment> paymentList(String username);

    int updatePartialRefundStatus(@Param("paymentNo") int paymentNo);

    PaymentDTO getPaymentByNo(int paymentNo);

    void updateDelivery(int paymentNo);

    void deliveryState(@Param("paymentNo") int paymentNo, @Param("delivery_code")String deliveryCode);

    void insertDelivery(PaymentDTO paymentDTO);

    void stateSuccess(int payment_no);

    List<PaymentDTO> paymentHistoryDetails(@Param("username") String username, @Param("imPortId") String imPortId);

    void paymentPurchaseDelivery(int paymentNo);

    List<PaymentDTO> orderHistoryDetails(@Param("seller") String username, @Param("imPortId") String imPortId);
}