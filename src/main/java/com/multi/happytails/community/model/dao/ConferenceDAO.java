package com.multi.happytails.community.model.dao;

import com.multi.happytails.community.model.dto.ConferenceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConferenceDAO {

    List<ConferenceDTO> findAll(String sortOrder);

    ConferenceDTO findById(Long ConferenceNo);

    void delete(Long ConferenceNo);

    void insert(ConferenceDTO ConferenceDTO);

    public int getCurrenConferenceNo();

    void cfcommendCount(Long ConferenceNo);

    List<ConferenceDTO> search(String keyword);

    void update(ConferenceDTO conference);

}
