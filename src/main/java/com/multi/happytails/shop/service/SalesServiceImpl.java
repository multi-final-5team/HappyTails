package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dao.SalesGoodsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private SalesGoodsDAO salesGoodsDAO;
}
