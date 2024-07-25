package com.multi.happytails.community.model.dao;

import com.multi.happytails.community.model.dto.DogloveDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DogloveDAO {

    List<DogloveDTO> findAll(String sortOrder);

    DogloveDTO findById(Long dogloveNo);

    void update(DogloveDTO dogloveDTO);

    void delete(Long dogloveNo);

    void dogloveInsert(DogloveDTO dogloveDTO);

    public int getCurrentDogloveNo();





    void incrementRecommendCount(Long dogloveNo);

    void decrementRecommendCount(Long dogloveNo);

    List<DogloveDTO> selectDogLovesWithPaging(Map<String, Object> params);
    int getTotalDogLoveCount();}
