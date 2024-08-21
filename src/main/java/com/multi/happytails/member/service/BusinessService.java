package com.multi.happytails.member.service;

import com.multi.happytails.member.model.dto.BusinessDTO;
import com.multi.happytails.member.model.dao.BusinessDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    @Autowired
    private BusinessDAO businessDAO;

    public void registerBusiness(BusinessDTO businessDTO) {
        businessDAO.insertBusiness(businessDTO);
    }

    public List<BusinessDTO> getAllBusinesses() {
        return businessDAO.getAllBusinesses();
    }

    public void updateBusinessRegistrationFlag(int businessNo, String flag) {
        businessDAO.updateBusinessRegistrationFlag(businessNo, flag);
    }
}