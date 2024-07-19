package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesGoodsDAO {

    void insertSales(SalesGoodsDTO salesGoodsDTO);

}
