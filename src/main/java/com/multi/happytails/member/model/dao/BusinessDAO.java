package com.multi.happytails.member.model.dao;

import com.multi.happytails.member.model.dto.BusinessDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusinessDAO {

    void insertBusiness(BusinessDTO businessDTO);

    List<BusinessDTO> getAllBusinesses();

    void updateBusinessRegistrationFlag(@Param("businessNo") int businessNo, @Param("flag") String flag);

}