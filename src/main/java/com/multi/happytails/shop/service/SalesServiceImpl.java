package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dao.SalesGoodsDAO;
import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesGoodsDAO salesGoodsDAO;

    @Override
    public void insertSales(SalesGoodsDTO salesGoodsDTO) {
        salesGoodsDAO.insertSales(salesGoodsDTO);
    }
}
