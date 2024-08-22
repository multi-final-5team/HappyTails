package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dao.SalesGoodsDAO;
import com.multi.happytails.shop.model.dto.SalesGoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : SalesServiceImpl.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 상품 판매 관리 ServiceImpl
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesGoodsDAO salesGoodsDAO;

    @Override
    public void insertSales(SalesGoodsDTO salesGoodsDTO) {
        salesGoodsDAO.insertSales(salesGoodsDTO);
    }

    @Override
    public int updateSales(SalesGoodsDTO salesGoodsDTO) {
        return salesGoodsDAO.updateSales(salesGoodsDTO);
    }

    @Override
    public List<SalesGoodsDTO> salesList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return salesGoodsDAO.salesList(offset, pageSize);
    }

    @Override
    public List<SalesGoodsDTO> salesListBusiness(int page, int pageSize, String id) {
        int offset = (page - 1) * pageSize;
        return salesGoodsDAO.salesListBusiness(offset, pageSize, id);
    }

    @Override
    public void deleteSales(SalesGoodsDTO salesGoodsDTO) {
        salesGoodsDAO.deleteSales(salesGoodsDTO);
    }

    @Override
    public SalesGoodsDTO selectSales(SalesGoodsDTO salesGoodsDTO) {
        return salesGoodsDAO.selectSales(salesGoodsDTO);
    }

    @Override
    public int getSalesNo() {
        return salesGoodsDAO.getSalesNo();
    }

    @Override
    public int salesPageCount() {
        return salesGoodsDAO.salesPageCount();
    }

    @Override
    public List<SalesGoodsDTO> search(String keyword) {
        return salesGoodsDAO.search(keyword);
    }

    @Override
    public List<SalesGoodsDTO> getRandomProducts() {
        return salesGoodsDAO.getRandomProducts();
    }

    @Override
    public int salesPageCountForUser(String id) {
        return salesGoodsDAO.salesPageCountForUser(id);
    }
}
