package com.multi.happytails.community.service;

import com.multi.happytails.community.model.dao.DogloveDAO;
import com.multi.happytails.community.model.dto.DogloveDTO;
import com.multi.happytails.help.model.dto.HelpCategoryDto;
import com.multi.happytails.help.model.dto.InquiryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogloveService {

    private static final String CATEGORY_CODE = "DOGLOVE_CODE";

    @Autowired
    private DogloveDAO dogloveDAO;

    public DogloveService(DogloveDAO dogloveDAO) {
        this.dogloveDAO = dogloveDAO;
    }

    public List<DogloveDTO> findAll() {
        return dogloveDAO.findAll("date");
    }

    public List<DogloveDTO> findAllSortedByDate() {
        return dogloveDAO.findAll("date");
    }

    public List<DogloveDTO> findAllSortedByRecommendation() {
        return dogloveDAO.findAll("recommendCount");
    }

    public DogloveDTO findById(Long dogloveNo) {
        return dogloveDAO.findById(dogloveNo);
    }

    public long dogloveInsert(DogloveDTO dogloveDTO) {
        dogloveDTO.setCategoryCode(CATEGORY_CODE);
        dogloveDAO.dogloveInsert(dogloveDTO);
        return dogloveDAO.getCurrentDogloveNo();
    }

    public void incrementRecommendCount(Long dogloveNo) {
        dogloveDAO.incrementRecommendCount(dogloveNo);
    }
    // 게시글 추천 수 감소
    public void decrementRecommendCount(Long dogloveNo) {
        dogloveDAO.decrementRecommendCount(dogloveNo);
    }


}
