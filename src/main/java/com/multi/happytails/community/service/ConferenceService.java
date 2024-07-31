package com.multi.happytails.community.service;

import com.multi.happytails.community.model.dao.ConferenceDAO;
import com.multi.happytails.community.model.dto.ConferenceDTO;
import com.multi.happytails.community.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceService {

    private static final String CATEGORY_CODE = "CONFERENCE_CODE";

    @Autowired
    private ReplyService replyService;


    @Autowired
    private ConferenceDAO conferenceDAO;

    public ConferenceService(ConferenceDAO conferenceDAO) { this.conferenceDAO = conferenceDAO; }

    public List<ConferenceDTO> findAllSortedByDate() {
        return conferenceDAO.findAll("date");
    }

    public List<ConferenceDTO> findAllSortedByRecommendation() {
        return conferenceDAO.findAll("recommendCount");
    }

    public ConferenceDTO findById(Long conferenceNo) {
        return conferenceDAO.findById(conferenceNo);
    }

    public long insert(ConferenceDTO conferenceDTO) {
        conferenceDTO.setCategoryCode(CATEGORY_CODE);
        conferenceDAO.insert(conferenceDTO);
        return conferenceDAO.getCurrenConferenceNo();
    }

    public void cfcommendCount(Long conferenceNo, String userId) { conferenceDAO.cfcommendCount(conferenceNo);
    }

    public List<ConferenceDTO>search(String keyword) {
        return conferenceDAO.search(keyword);
    }

    public void delete(Long conferenceNo) {
        conferenceDAO.delete(conferenceNo);
    }


    public void update(ConferenceDTO conference) {
        Long conferenceNo = conference.getConferenceNo();
        conferenceDAO.update(conference);
    }
}
