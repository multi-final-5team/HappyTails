package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.OrderlistDTO;
import com.multi.happytails.shop.model.dto.PaymentDTO;
import com.multi.happytails.shop.model.dto.PaymentpageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentDAO {

    PaymentpageDTO selectpaymentpage(@Param("username") String username);

    int insertPayment(PaymentDTO paymentDTO);

    List<OrderlistDTO> selectOrders(String memberName);

    int updateRefundStatus(String imPortId);
}