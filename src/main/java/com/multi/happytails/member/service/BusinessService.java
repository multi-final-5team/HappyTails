package com.multi.happytails.member.service;

import com.multi.happytails.member.model.dto.BusinessDTO;
import com.multi.happytails.member.model.dao.BusinessDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    @Autowired
    private BusinessDAO businessDAO;

    public void registerBusiness(BusinessDTO businessDTO) {
        businessDAO.insertBusiness(businessDTO);
    }
}