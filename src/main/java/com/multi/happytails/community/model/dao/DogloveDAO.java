package com.multi.happytails.community.model.dao;

import com.multi.happytails.community.model.dto.DogloveDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DogloveDAO {

    List<DogloveDTO> findAll(String sortOrder);

    DogloveDTO findById(Long dogloveNo);

    void delete(Long dogloveNo);

    void dogloveInsert(DogloveDTO dogloveDTO);

    public int getCurrentDogloveNo();

    List<DogloveDTO> search(String keyword);

    int update(DogloveDTO doglove);

    void dgRecommendCount(Long dogloveNo);
}
