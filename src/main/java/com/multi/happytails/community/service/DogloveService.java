package com.multi.happytails.community.service;

import com.multi.happytails.community.model.dao.DogloveDAO;
import com.multi.happytails.community.model.dto.DogloveDTO;
import com.multi.happytails.community.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogloveService {

    private static final String CATEGORY_CODE = "DOGLOVE_CODE";

    @Autowired
    private ReplyService replyService;

    @Autowired
    private DogloveDAO dogloveDAO;

    public DogloveService(DogloveDAO dogloveDAO) {
        this.dogloveDAO = dogloveDAO;
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

    public void dgRecommendCount(Long dogloveNo, String userId) {
        dogloveDAO.dgRecommendCount(dogloveNo);
    }

    public List<DogloveDTO>search(String keyword) {
        return dogloveDAO.search(keyword);
    }

    public void delete(Long dogloveNo) {
        dogloveDAO.delete(dogloveNo);
    }

    public int update(DogloveDTO dogloveDTO) {
        return dogloveDAO.update(dogloveDTO);
    }

    public List<DogloveDTO> mainDogLoveList() {
        return dogloveDAO.mainDogLoveList();
    }
}



