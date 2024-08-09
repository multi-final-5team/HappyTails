package com.multi.happytails.member.model.dao;

import com.multi.happytails.member.model.dto.BusinessDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessDAO {
    void insertBusiness(BusinessDTO businessDTO);
}